package com.mall.common.domain.dto.req;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WeiXinPortalValidateReqDto {

    private String signature;

    private String timestamp;

    private String nonce;

    private String echostr;
}
