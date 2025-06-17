package com.mall.mvcservice.controller;


import com.mall.mvcservice.service.LoginService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("${mall-mvc.request.prefix}/login")
public class LoginController {

    @Resource
    private LoginService loginService;
}
