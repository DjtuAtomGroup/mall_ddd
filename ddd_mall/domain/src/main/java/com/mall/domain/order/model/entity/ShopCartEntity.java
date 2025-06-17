package com.mall.domain.order.model.entity;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShopCartEntity {

    private String userId;

    private String productId;
}
