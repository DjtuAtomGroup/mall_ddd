package com.mall.common.domain.dto.resp;


import com.mall.common.domain.enums.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayOrderRespDto {

    private String userId;

    private String orderId;

    private String payUrl;

    private OrderStatusEnum orderStatusEnum;
}
