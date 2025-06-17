package com.mall.domain.order.service.impl;

import com.alipay.api.AlipayClient;
import com.mall.api.dto.req.PrepayOrderReqDto;
import com.mall.api.service.PayService;
import com.mall.domain.order.model.entity.PayOrderEntity;
import com.mall.domain.order.service.PayOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
@Slf4j
public class PayOrderServiceImpl implements PayOrderService {

    @Resource
    private AlipayClient alipayClient;

    @Resource
    private PayService payService;

    @Override
    public PayOrderEntity doPrepayOrder(PrepayOrderReqDto params) {
        return null;
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
}
