package com.mall.mvcservice.controller;


import com.mall.common.domain.dto.req.WeiXinPortalValidateReqDto;
import com.mall.common.domain.vo.Result;
import com.mall.mvcservice.service.LoginService;
import com.mall.mvcservice.service.WeiXinPortalService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("${mall-mvc.request.prefix}/wx")
public class WeiXinPortalController {


    private String originalId;

    private String token;

    @Resource
    private LoginService loginService;

    @Resource
    private WeiXinPortalService portalService;



    @GetMapping("/validate")
    public Result<String> validate(
            @RequestParam String signature,
            @RequestParam String timestamp,
            @RequestParam String nonce,
            @RequestParam String echostr
    ) {
        WeiXinPortalValidateReqDto params = WeiXinPortalValidateReqDto.builder()
                .nonce(nonce)
                .echostr(echostr)
                .signature(signature)
                .timestamp(timestamp)
                .build();
        return Result.success(portalService.msgValidate(params));
    }
}
