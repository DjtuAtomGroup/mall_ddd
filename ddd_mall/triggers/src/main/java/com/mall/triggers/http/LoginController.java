package com.mall.triggers.http;


import com.mall.api.response.Response;
import com.mall.domain.auth.service.ILoginService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("${project.config.request.prefix}/login")
public class LoginController {

    @Resource
    private ILoginService loginService;



    @GetMapping("/createQrCode")
    public Response<String> createQrCodeTicket() {
        try {
            String qrCodeTicket = loginService.createQrCodeTicket();
            log.info("生成登录二维码成功, {}", qrCodeTicket);
            return Response.success(qrCodeTicket);
        } catch (Exception e) {
            log.error("生成登录二维码失败, {}", e.getMessage());
            return Response.fail("生成登录二维码失败");
        }
    }


    @GetMapping("/checkLogin/{ticket}")
    public Response<String> checkLogin(
            @PathVariable String ticket
    ) {
        try {
            String openIdToken = loginService.checkLogin(ticket);
            log.info("扫描检测登录结果, ticket: {}, openIdToken: {}", ticket, openIdToken);
            if (StringUtils.isNotBlank(openIdToken)) {
                return Response.success(openIdToken);
            } else {
                return Response.unAuthorized("登录失败");
            }
        } catch (Exception e) {
            log.error("验证登录失败, {}", e.getMessage());
            return Response.fail("验证登录失败");
        }
    }
}
