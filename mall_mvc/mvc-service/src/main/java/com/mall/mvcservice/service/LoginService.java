package com.mall.mvcservice.service;

public interface LoginService {

    String createQrCodeTicket();

    String checkLogin(String ticket);

    void saveLoginStatus(String ticket, String openId);
}
