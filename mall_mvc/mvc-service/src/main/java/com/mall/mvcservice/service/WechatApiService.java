package com.mall.mvcservice.service;

import com.mall.common.domain.dto.resp.WeiXInTokenRespDto;
import com.mall.common.domain.dto.resp.WeiXinQrCodeRespDto;
import com.mall.common.domain.vo.WeiXinTemplateMessageVo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WechatApiService {


    /**
     * 获取 Access Token 接口
     * @param grantType client_credential
     * @param uuid 第三方用户的唯一凭证
     * @param appSecret 第三方用户的唯一凭证密钥
     * @return
     */
    @GET("/cgi-bin/token")
    Call<WeiXInTokenRespDto> getToken(
            @Query("grant_type") String grantType,
            @Query("appid") String uuid,
            @Query("secret") String appSecret
            );


    /**
     * 创建二维码接口
     * @param accessToken  getToken 获取的 access_token
     * @param qrCodeRespDto  weixinQrCodeReq 的入参对象
     * @return  应答结果
     */
    @POST("/cgi-bin/qrcode/create")
    Call<WeiXinQrCodeRespDto> createQrCode(
            @Query("access_token") String accessToken,
            @Body WeiXinQrCodeRespDto qrCodeRespDto
    );


    /**
     * 发送微信公众号模板消息
     * @param accessToken  getToken 获取的 access_token 信息
     * @return  应答结果
     */
    @POST("/cgi-bin/message/templates/send")
    Call<Void> sendMessage(
            @Query("access_token") String accessToken,
            @Body WeiXinTemplateMessageVo messageVo
    );
}
