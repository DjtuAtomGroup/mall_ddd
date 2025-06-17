package com.mall.mvcservice.service.impl;

import com.google.protobuf.ServiceException;
import com.mall.common.domain.dto.req.ReceiveWeiXinMsgReqDto;
import com.mall.common.domain.dto.req.WeiXinPortalValidateReqDto;
import com.mall.mvcservice.service.WeiXinPortalService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class WeiXInPortalServiceImpl implements WeiXinPortalService {

    /**
     * 微信公众号验签信息验证
     *
     * @param params
     * @return
     */
    @Override
    @SneakyThrows
    public String msgValidate(WeiXinPortalValidateReqDto params) {
        try {
            String signature = params.getSignature();
            String nonce = params.getNonce();
            String echostr = params.getEchostr();
            String timestamp = params.getTimestamp();
            log.info("微信公众号验签信息开始, [{}, {}, {}, {}]", signature, timestamp, nonce, echostr);
            if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
                log.error("微信公众号验签信息失败, 参数缺失");
                throw new ServiceException("微信公众号验签信息失败");
            }
            boolean check = false;
            // check = SignatureUtil.check(signature, timestamp, nonce, echostr);
            if (!check) {
                return null;
            }
            return echostr;
        } catch (Exception e) {
            log.error("微信公众号验签信息失败, {}", e.getMessage());
            throw new ServiceException("微信公众号验签信息失败");
        }
    }

    /**
     * 接收微信公众号消息请求
     *
     * @param params
     * @return
     */
    @Override
    public String receiveMsg(ReceiveWeiXinMsgReqDto params) {
        return null;
    }
}
