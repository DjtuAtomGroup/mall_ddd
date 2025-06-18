package com.mall.domain.auth.service;

public interface ILoginService {

    String createQrCodeTicket();

    String checkLogin(String ticket);

    void saveLoginStatus(String ticket, String openId);
}
