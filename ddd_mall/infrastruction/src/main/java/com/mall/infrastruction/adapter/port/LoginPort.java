package com.mall.infrastruction.adapter.port;

import com.google.common.cache.Cache;
import com.mall.domain.auth.adpter.port.ILoginPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
@Slf4j
public class LoginPort implements ILoginPort {


    @Value("${weixin.config.app-id}")
    private String appId;

    @Value("${weixin.config.app-secret}")
    private String appSecret;

    @Value("${weixin.config.template_id}")
    private String templateId;

    @Resource
    private Cache<String, String> weiXinAccessToken;

    @Resource

    @Override
    public String createQrCodeTicket() {
        return null;
    }

    @Override
    public void sendLoginTemplateMessage(String openId) {

    }
}
