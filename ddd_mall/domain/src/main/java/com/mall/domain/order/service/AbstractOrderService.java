package com.mall.domain.order.service;

import com.mall.api.dto.req.PrepayOrderReqDto;
import com.mall.domain.order.adpter.port.IProductPort;
import com.mall.domain.order.adpter.repository.IOrderRepository;
import com.mall.domain.order.model.aggregation.CreateOrderAggregate;
import com.mall.domain.order.model.entity.OrderEntity;
import com.mall.domain.order.model.entity.PayOrderEntity;
import com.mall.domain.order.model.entity.ProductEntity;
import com.mall.domain.order.model.entity.ShopCartEntity;
import com.mall.domain.order.model.valobj.OrderStatusVo;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractOrderService implements PayOrderService {


    protected final IOrderRepository repository;

    protected final IProductPort productPort;


    protected abstract void doSaveOrder(CreateOrderAggregate params);

    protected abstract PayOrderEntity doPreOrder(PrepayOrderReqDto params);


    @Override
    @SneakyThrows
    public PayOrderEntity createOrder(ShopCartEntity params) {
        OrderEntity unpaidOrderEntity = repository.queryUnpaidOrder(params);
        if (unpaidOrderEntity != null && unpaidOrderEntity.getOrderStatus().equals(OrderStatusVo.PAY_WAIT)) {
            log.info(
                    "创建订单-已存在, 存在未支付订单, userId: {}, productId: {}, orderId: {}",
                    params.getUserId(),
                    params.getProductId(),
                    unpaidOrderEntity.getOrderId()
            );
            return PayOrderEntity.builder()
                    .orderId(unpaidOrderEntity.getOrderId())
                    .payUrl(unpaidOrderEntity.getPayUrl())
                    .build();
        } else if (unpaidOrderEntity != null && unpaidOrderEntity.getOrderStatus().equals(OrderStatusVo.CREATE)) {
            log.info(
                    "创建订单-已存在, 存在未创建支付单订单, userId: {}, orderId: {}, productId: {}",
                    params.getUserId(),
                    unpaidOrderEntity.getOrderId(),
                    params.getProductId()
            );
            PrepayOrderReqDto reqDto = PrepayOrderReqDto.builder()
                    .orderId(unpaidOrderEntity.getOrderId())
                    .productId(params.getProductId())
                    .productName(unpaidOrderEntity.getProductName())
                    .totalAmount(unpaidOrderEntity.getTotalAmount())
                    .userId(params.getUserId())
                    .build();
            PayOrderEntity payOrderEntity = this.doPreOrder(reqDto);
            return PayOrderEntity.builder()
                    .orderId(payOrderEntity.getOrderId())
                    .payUrl(payOrderEntity.getPayUrl())
                    .build();
        }

        // 查询订单
        ProductEntity productEntity = productPort.queryProductById(params.getProductId());

        OrderEntity orderEntity = CreateOrderAggregate.buildOrderEntity(
                productEntity.getProductId(),
                productEntity.getProductName()
        );

        CreateOrderAggregate aggregate = CreateOrderAggregate.builder()
                .userId(params.getUserId())
                .productEntity(productEntity)
                .orderEntity(orderEntity)
                .build();

        // 保存订单
        this.doSaveOrder(aggregate);

        PrepayOrderReqDto reqDto = PrepayOrderReqDto.builder()
                .userId(params.getUserId())
                .productId(productEntity.getProductId())
                .productName(productEntity.getProductName())
                .orderId(orderEntity.getOrderId())
                .totalAmount(productEntity.getPrice())
                .build();
        PayOrderEntity payOrderEntity = this.doPreOrder(reqDto);
        log.info(
                    "创建订单-完成, 生成订单支付单, userId: {}, orderId: {}, payUrl: {}",
                    params.getUserId(),
                    orderEntity.getOrderId(),
                    payOrderEntity.getPayUrl()
                );
        return PayOrderEntity.builder()
                .orderId(payOrderEntity.getOrderId())
                .payUrl(payOrderEntity.getPayUrl())
                .build();
    }
}
