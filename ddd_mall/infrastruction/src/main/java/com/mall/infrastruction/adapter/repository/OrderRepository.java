package com.mall.infrastruction.adapter.repository;


import com.alibaba.fastjson.JSON;
import com.google.common.eventbus.EventBus;
import com.mall.domain.order.adpter.repository.IOrderRepository;
import com.mall.domain.order.event.PaySuccessMessageEvent;
import com.mall.domain.order.model.aggregation.CreateOrderAggregate;
import com.mall.domain.order.model.entity.OrderEntity;
import com.mall.domain.order.model.entity.PayOrderEntity;
import com.mall.domain.order.model.entity.ProductEntity;
import com.mall.domain.order.model.entity.ShopCartEntity;
import com.mall.domain.order.model.valobj.OrderStatusVo;
import com.mall.infrastruction.dao.IOrderDao;
import com.mall.infrastruction.dao.po.PayOrder;
import com.mall.infrastruction.redis.IRedisService;
import com.mall.types.events.BaseEvent;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
@Slf4j
public class OrderRepository implements IOrderRepository {


    @Resource
    private EventBus eventBus;

    @Resource
    private IRedisService redisService;

    @Resource
    private IOrderDao orderDao;

    @Resource
    private PaySuccessMessageEvent paySuccessMessageEvent;


    /**
     * 查询未支付订单
     *
     * @param params
     * @return
     */
    @Override
    @SneakyThrows
    public OrderEntity queryUnpaidOrder(ShopCartEntity params) {
        // 1. 封装参数
        PayOrder orderReq = PayOrder.builder()
                .userId(params.getUserId())
                .productId(params.getProductId())
                .build();
        // 2. 查询订单
        PayOrder order = orderDao.queryUnPayOrder(orderReq);
        if (order == null) return null;
        // 3. 返回结果
        return OrderEntity.builder()
                .productId(order.getProductId())
                .productName(order.getProductName())
                .orderId(order.getOrderId())
                .orderStatus(OrderStatusVo.valueOf(order.getStatus()))
                .orderTime(order.getOrderTime())
                .totalAmount(order.getTotalAmount())
                .payUrl(order.getPayUrl())
                .build();
    }

    /**
     * 保存订单对象
     *
     * @param params
     */
    @Override
    public void doSaveOrder(CreateOrderAggregate params) {
        String userId = params.getUserId();
        ProductEntity productEntity = params.getProductEntity();
        OrderEntity orderEntity = params.getOrderEntity();

        PayOrder order = PayOrder.builder()
                .userId(userId)
                .productId(productEntity.getProductId())
                .productName(productEntity.getProductName())
                .orderId(orderEntity.getOrderId())
                .orderTime(orderEntity.getOrderTime())
                .totalAmount(productEntity.getPrice())
                .status(orderEntity.getOrderStatus().getCode())
                .build();

        orderDao.insert(order);

        redisService.setValue(PayOrder.cacheKey(userId, orderEntity.getOrderId()), order);
    }

    /**
     * 更新订单支付信息
     *
     * @param params
     */
    @Override
    public void updateOrderPayInfo(PayOrderEntity params) {
        PayOrder order = PayOrder.builder()
                .userId(params.getUserId())
                .orderId(params.getOrderId())
                .payUrl(params.getPayUrl())
                .status(params.getOrderStatus().getCode())
                .build();
        orderDao.updateOrderPayInfo(order);
    }

    /**
     * 订单支付成功回调
     *
     * @param orderId
     */
    @Override
    public void changeOrderPaySuccess(String orderId) {
        PayOrder order = PayOrder.builder()
                .orderId(orderId)
                .status(OrderStatusVo.PAY_SUCCESS.getCode())
                .build();
        orderDao.changeOrderPaySuccess(order);

        // 发送支付成功消息
        BaseEvent.EventMessage<PaySuccessMessageEvent.PaySuccessMessage> message = paySuccessMessageEvent.buildEventMessage(PaySuccessMessageEvent.PaySuccessMessage.builder().tradeNo(orderId).build());
        PaySuccessMessageEvent.PaySuccessMessage paySuccessMessage = message.getData();

        eventBus.post(JSON.toJSONString(paySuccessMessage));
    }

    @Override
    public List<String> queryUnpaidOrder() {
        return orderDao.queryNoPayNotifyOrder();
    }

    @Override
    public List<String> queryTimeoutOrder() {
        return orderDao.queryTimeoutCloseOrderList();
    }

    @Override
    public boolean changeOrderClose(String orderId) {
        return orderDao.changeOrderClose(orderId);
    }
}
