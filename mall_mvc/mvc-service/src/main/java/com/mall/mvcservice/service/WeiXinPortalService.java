package com.mall.mvcservice.service;

import com.mall.common.domain.dto.req.ReceiveWeiXinMsgReqDto;
import com.mall.common.domain.dto.req.WeiXinPortalValidateReqDto;

public interface WeiXinPortalService {


    /**
     * 微信公众号验签信息验证
     * @param params
     * @return
     */
    String msgValidate(WeiXinPortalValidateReqDto params);


    /**
     * 接收微信公众号消息请求
     * @param params
     * @return
     */
    String receiveMsg(ReceiveWeiXinMsgReqDto params);
}
