package com.mall.domain.order.service;

import com.mall.api.dto.req.PrepayOrderReqDto;
import com.mall.domain.order.model.entity.PayOrderEntity;
import com.mall.domain.order.model.entity.ShopCartEntity;

import java.util.List;

public interface PayOrderService {

    PayOrderEntity createOrder(ShopCartEntity params);

    void changeOrderPaySuccess(String orderId);

    List<String> queryUnpaidOrder();

    List<String> queryTimeoutOrder();

    boolean changeOrderClose(String orderId);
}
