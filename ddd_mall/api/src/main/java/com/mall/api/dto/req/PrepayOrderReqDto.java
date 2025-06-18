package com.mall.api.dto.req;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrepayOrderReqDto {

    private String userId;

    private String productId;

    private String productName;

    private String orderId;

    private BigDecimal totalAmount;
}
