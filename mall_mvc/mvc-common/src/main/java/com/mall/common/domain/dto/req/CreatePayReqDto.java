package com.mall.common.domain.dto.req;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePayReqDto {

    private String userId;

    private String productId;
}
