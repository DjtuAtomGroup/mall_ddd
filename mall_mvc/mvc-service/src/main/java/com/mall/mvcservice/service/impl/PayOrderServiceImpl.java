package com.mall.mvcservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.eventbus.EventBus;
import com.google.protobuf.ServiceException;
import com.mall.common.dao.PayOrderDao;
import com.mall.common.domain.dto.req.ShopCartReqDto;
import com.mall.common.domain.dto.resp.PayOrderRespDto;
import com.mall.common.domain.enums.OrderStatusEnum;
import com.mall.common.domain.po.PayOrder;
import com.mall.common.domain.vo.ProductVo;
import com.mall.common.mapper.PayOrderMapper;
import com.mall.mvcservice.service.PayOrderService;
import com.mall.mvcservice.service.ProductRPC;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
* @author YJH
* @description 针对表【pay_order】的数据库操作Service实现
* @createDate 2025-06-16 09:53:33
*/
@Service
@Slf4j
public class PayOrderServiceImpl extends ServiceImpl<PayOrderMapper, PayOrder>
    implements PayOrderService {


    @Value("${alipay.notify_url}")
    private String notifyUrl;

    @Value("${alipay.return_url}")
    private String returnUrl;

    @Resource
    private PayOrderMapper payOrderMapper;

    @Resource
    private ProductRPC productRPC;

    @Resource
    private AlipayClient alipayClient;

    @Resource
    private EventBus eventBus;

    @Resource
    private PayOrderDao orderDao;

    @Override
    @SneakyThrows
    public PayOrderRespDto createPayOrder(ShopCartReqDto params) {
        try {
            PayOrder order = PayOrder.builder()
                    .userId(params.getUserId())
                    .productId(params.getProductId())
                    .build();

            PayOrder unpaidOrder = orderDao.queryUnPayOrder(order);

            if (unpaidOrder == null) {
                log.info("创建订单-存在，已存在未支付订单。userId:{} productId:{} orderId:{}",
                        params.getUserId(),
                        params.getProductId(),
                        unpaidOrder.getOrderId()
                );
                return PayOrderRespDto.builder()
                        .orderId(unpaidOrder.getOrderId())
                        .payUrl(unpaidOrder.getPayUrl())
                        .build();
            } else if (null != unpaidOrder && OrderStatusEnum.CREATE.getCode().equals(unpaidOrder.getStatus())) {
                log.info("创建订单-存在，存在未创建支付单订单，创建支付单开始 userId:{} productId:{} orderId:{}",
                        params.getUserId(),
                        params.getProductId(),
                        unpaidOrder.getOrderId()
                );
                PayOrder payOrder = doPrepayOrder(unpaidOrder.getProductId(), unpaidOrder.getProductName(), unpaidOrder.getOrderId(), unpaidOrder.getTotalAmount());
                return PayOrderRespDto.builder()
                        .orderId(payOrder.getOrderId())
                        .payUrl(payOrder.getPayUrl())
                        .build();
            }

            // 2. 查询商品 & 创建订单
            ProductVo productVO = productRPC.queryProductById(params.getProductId());
            String orderId = RandomStringUtils.randomNumeric(16);
            orderDao.insert(PayOrder.builder()
                    .userId(params.getUserId())
                    .productId(params.getProductId())
                    .productName(productVO.getProductName())
                    .orderId(orderId)
                    .totalAmount(productVO.getPrice())
                    .orderTime(new Date())
                    .status(OrderStatusEnum.CREATE.getCode())
                    .build());

            // 3. 创建支付单
            PayOrder payOrder = doPrepayOrder(productVO.getProductId(), productVO.getProductName(), orderId, productVO.getPrice());

            return PayOrderRespDto.builder()
                    .orderId(orderId)
                    .payUrl(payOrder.getPayUrl())
                    .build();
        } catch (Exception e) {
            log.error("创建订单失败, {}", e.getMessage());
            throw new ServiceException("创建订单失败");
        }
    }

    @Override
    public void changeOrderPayStatus(String orderId) {
        PayOrder payOrderReq = PayOrder.builder()
                .orderId(orderId)
                .status(OrderStatusEnum.PAY_WAIT.getCode())
                .build();
        orderDao.changeOrderPaySuccess(payOrderReq);

        eventBus.post(JSON.toJSONString(payOrderReq));
    }

    @Override
    public List<String> queryUnPayOrders() {
        return orderDao.queryNoPayNotifyOrder();
    }

    @Override
    public List<String> queryTimeoutOrders() {
        return orderDao.queryTimeoutCloseOrderList();
    }

    @Override
    public boolean changeOrderClose(String orderId) {
        return orderDao.changeOrderClose(orderId);
    }




    @SneakyThrows
    private PayOrder doPrepayOrder(String productId, String productName, String orderId, BigDecimal totalAmount) throws AlipayApiException {
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setNotifyUrl(notifyUrl);
        request.setReturnUrl(returnUrl);

        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", orderId);
        bizContent.put("total_amount", totalAmount.toString());
        bizContent.put("subject", productName);
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");
        request.setBizContent(bizContent.toString());

        String form = alipayClient.pageExecute(request).getBody();

        PayOrder payOrder = PayOrder.builder()
                .orderId(orderId)
                .payUrl(form)
                .status(OrderStatusEnum.PAY_WAIT.getCode())
                .build();

        orderDao.updateOrderPayInfo(payOrder);

        return payOrder;
    }
}




