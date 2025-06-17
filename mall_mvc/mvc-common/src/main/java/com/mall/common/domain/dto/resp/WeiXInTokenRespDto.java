package com.mall.common.domain.dto.resp;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeiXInTokenRespDto {

    private String access_token;

    private int expire_in;

    private String errCode;

    private String errMsg;
}
