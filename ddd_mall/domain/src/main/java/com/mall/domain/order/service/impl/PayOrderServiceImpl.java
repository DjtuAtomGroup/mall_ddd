package com.mall.domain.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePayRequest;
import com.google.protobuf.ServiceException;
import com.mall.api.dto.req.PrepayOrderReqDto;
import com.mall.api.service.PayService;
import com.mall.domain.order.adpter.repository.IOrderRepository;
import com.mall.domain.order.model.aggregation.CreateOrderAggregate;
import com.mall.domain.order.model.entity.PayOrderEntity;
import com.mall.domain.order.model.valobj.OrderStatusVo;
import com.mall.domain.order.service.AbstractOrderService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;


@Service
@Slf4j
public class PayOrderServiceImpl extends AbstractOrderService {

    @Resource
    private AlipayClient alipayClient;

    @Resource
    private PayService payService;

    private String notifyUrl;

    private String returnUrl;

    public PayOrderServiceImpl(IOrderRepository repository) {
        super(repository);
    }

    @Override
    public void changeOrderPaySuccess(String orderId) {

    }

    @Override
    public List<String> queryUnpaidOrder() {
        return null;
    }

    @Override
    public List<String> queryTimeoutOrder() {
        return null;
    }

    @Override
    public boolean changeOrderClose(String orderId) {
        return false;
    }


    @Override
    @SneakyThrows
    protected PayOrderEntity doPreOrder(PrepayOrderReqDto params) {
        try {
            AlipayTradePayRequest request = new AlipayTradePayRequest();
            request.setNotifyUrl(notifyUrl);
            request.setReturnUrl(returnUrl);

            String orderId = params.getOrderId();
            String productId = params.getProductId();
            String productName = params.getProductName();
            String userId = params.getUserId();
            BigDecimal totalAmount = params.getTotalAmount();

            JSONObject bizContent = new JSONObject();
            bizContent.put("out_trade_no", orderId);
            bizContent.put("total_amount", totalAmount.toString());
            bizContent.put("subject", productName);
            bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");
            request.setBizContent(bizContent.toString());

            String form = alipayClient.pageExecute(request).getBody();

            PayOrderEntity payOrder = PayOrderEntity.builder()
                    .orderId(orderId)
                    .payUrl(form)
                    .orderStatus(OrderStatusVo.PAY_WAIT)
                    .build();

            repository.updateOrderPayInfo(payOrder);

            return payOrder;
        } catch (Exception e) {
            log.error("预付订单失败, {}", e.getMessage());
            throw new ServiceException("预付订单失败");
        }
    }

    @Override
    protected void doSaveOrder(CreateOrderAggregate params) {

    }
}
