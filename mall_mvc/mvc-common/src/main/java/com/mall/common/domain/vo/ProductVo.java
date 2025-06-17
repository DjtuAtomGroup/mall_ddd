package com.mall.common.domain.vo;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductVo {

    private String productId;

    private String productName;

    private String productDesc;

    private BigDecimal price;
}
