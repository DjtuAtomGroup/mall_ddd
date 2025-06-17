package com.mall.domain.order.service;

import com.mall.api.dto.req.PrepayOrderReqDto;
import com.mall.domain.order.adpter.repository.IOrderRepository;
import com.mall.domain.order.model.aggregation.CreateOrderAggregate;
import com.mall.domain.order.model.entity.PayOrderEntity;
import com.mall.domain.order.model.entity.ShopCartEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractOrderService implements PayOrderService {


    protected final IOrderRepository repository;


    protected abstract void doSaveOrder(CreateOrderAggregate params);

    protected abstract PayOrderEntity doPreOrder(PrepayOrderReqDto params);


    @Override
    public PayOrderEntity createOrder(ShopCartEntity params) {
        return null;
    }
}
