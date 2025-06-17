package com.mall.infrastruction.adapter.repository;


import com.google.common.eventbus.EventBus;
import com.mall.domain.order.adpter.repository.IOrderRepository;
import com.mall.domain.order.model.aggregation.CreateOrderAggregate;
import com.mall.domain.order.model.entity.OrderEntity;
import com.mall.domain.order.model.entity.PayOrderEntity;
import com.mall.domain.order.model.entity.ShopCartEntity;
import com.mall.infrastruction.dao.IOrderDao;
import com.mall.infrastruction.redis.IRedisService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class OrderRepository implements IOrderRepository {


    @Resource
    private EventBus eventBus;

    @Resource
    private IRedisService redisService;

    @Resource
    private IOrderDao orderDao;


    /**
     * 查询未支付订单
     *
     * @param params
     * @return
     */
    @Override
    public OrderEntity queryUnpaidOrder(ShopCartEntity params) {
        return null;
    }

    /**
     * 保存订单对象
     *
     * @param params
     */
    @Override
    public void doSaveOrder(CreateOrderAggregate params) {

    }

    /**
     * 更新订单支付信息
     *
     * @param params
     */
    @Override
    public void updateOrderPayInfo(PayOrderEntity params) {

    }

    /**
     * 订单支付成功回调
     *
     * @param orderId
     */
    @Override
    public void changeOrderPaySuccess(String orderId) {

    }
}
