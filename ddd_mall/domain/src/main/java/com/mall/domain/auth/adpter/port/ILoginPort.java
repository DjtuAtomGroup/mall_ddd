package com.mall.domain.auth.adpter.port;

public interface ILoginPort {

    String createQrCodeTicket();

    void sendLoginTemplateMessage(String openId);
}
