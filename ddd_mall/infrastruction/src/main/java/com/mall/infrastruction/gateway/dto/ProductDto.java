package com.mall.infrastruction.gateway.dto;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductDto {

    private String productId;

    private String productName;

    private String productDesc;

    private BigDecimal price;
}
