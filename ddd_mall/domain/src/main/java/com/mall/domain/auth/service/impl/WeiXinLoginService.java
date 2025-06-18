package com.mall.domain.auth.service.impl;

import com.google.common.cache.Cache;
import com.google.protobuf.ServiceException;
import com.mall.domain.auth.adpter.port.ILoginPort;
import com.mall.domain.auth.service.ILoginService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
@Slf4j
public class WeiXinLoginService implements ILoginService {


    @Resource
    private ILoginPort loginPort;

    @Resource
    private Cache<String, String> openidToken;


    @Override
    @SneakyThrows
    public String createQrCodeTicket() {
        try {
            return loginPort.createQrCodeTicket();
        } catch (Exception e) {
            log.error("创建二维码失败, {}", e.getMessage());
            throw new ServiceException("创建二维码失败");
        }
    }

    @Override
    public String checkLogin(String ticket) {
        return openidToken.getIfPresent(ticket);
    }

    @Override
    public void saveLoginStatus(String ticket, String openId) {
        openidToken.put(ticket, openId);

        loginPort.sendLoginTemplateMessage(openId);
    }
}
