package com.mall.domain.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePayRequest;
import com.google.protobuf.ServiceException;
import com.mall.api.dto.req.PrepayOrderReqDto;
import com.mall.domain.order.adpter.port.IProductPort;
import com.mall.domain.order.adpter.repository.IOrderRepository;
import com.mall.domain.order.model.aggregation.CreateOrderAggregate;
import com.mall.domain.order.model.entity.PayOrderEntity;
import com.mall.domain.order.model.valobj.OrderStatusVo;
import com.mall.domain.order.service.AbstractOrderService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;


@Service
@Slf4j
public class PayOrderServiceImpl extends AbstractOrderService {

    @Resource
    private AlipayClient alipayClient;

    @Value("${alipay.notify_url}")
    private String notifyUrl;

    @Value("${alipay.return_url}")
    private String returnUrl;

    public PayOrderServiceImpl(IOrderRepository repository, IProductPort productPort) {
        super(repository, productPort);
    }

    @Override
    public void changeOrderPaySuccess(String orderId) {
        repository.changeOrderPaySuccess(orderId);
    }

    @Override
    public List<String> queryUnpaidOrder() {
        return repository.queryUnpaidOrder();
    }

    @Override
    public List<String> queryTimeoutOrder() {
        return repository.queryTimeoutOrder();
    }

    @Override
    public boolean changeOrderClose(String orderId) {
        return repository.changeOrderClose(orderId);
    }


    @Override
    @SneakyThrows
    protected PayOrderEntity doPreOrder(PrepayOrderReqDto params) {
        try {
            AlipayTradePayRequest request = new AlipayTradePayRequest();
            request.setNotifyUrl(notifyUrl);
            request.setReturnUrl(returnUrl);

            String orderId = params.getOrderId();
            String productName = params.getProductName();
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
        repository.doSaveOrder(params);
    }
}
