package com.mall.common.domain.dto.req;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReceiveWeiXinMsgReqDto {

    private String signature;

    private String timestamp;

    private String nonce;

    private String openid;

    private String encType;

    private String msgSignature;
}
