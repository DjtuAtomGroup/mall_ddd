package com.mall.common.domain.dto.resp;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeiXinQrCodeRespDto {

    private String ticket;

    private Long expire_seconds;

    private String url;
}
