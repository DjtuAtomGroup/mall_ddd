package com.mall.domain.order.model.aggregation;


import com.mall.domain.order.model.entity.OrderEntity;
import com.mall.domain.order.model.entity.ProductEntity;
import com.mall.domain.order.model.valobj.OrderStatusVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOrderAggregate {

    // 用户ID
    private String userId;
    // 商品实体对象
    private ProductEntity productEntity;
    // 订单实体对象
    private OrderEntity orderEntity;

    public static OrderEntity buildOrderEntity(String productId, String productName) {
        return OrderEntity.builder()
                .productId(productId)
                .productName(productName)
                .orderId(RandomStringUtils.randomNumeric(16))
                .orderTime(new Date())
                .orderStatus(OrderStatusVo.CREATE)
                .build();
    }
}
