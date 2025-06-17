package com.mall.mvcservice.service.impl;


import com.mall.mvcservice.service.LoginService;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Override
    public String createQrCodeTicket() {
        return null;
    }

    @Override
    public String checkLogin(String ticket) {
        return null;
    }

    @Override
    public void saveLoginStatus(String ticket, String openId) {

    }
}
