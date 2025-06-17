package com.mall.mvcservice.controller;


import com.mall.common.domain.dto.req.CreatePayReqDto;
import com.mall.common.domain.vo.Result;
import com.mall.mvcservice.service.AlipayService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class AlipayController {

    @Resource
    private AlipayService alipayService;


    @PostMapping("/mvc/pay/createOrder")
    public Result<String> createPayOrder(
            @RequestParam String userId,
            @RequestParam String productId
    ) {
        CreatePayReqDto params = CreatePayReqDto.builder()
                .userId(userId)
                .productId(productId)
                .build();
        return Result.success(alipayService.createPayOrder(params));
    }



    @GetMapping("/mvc/pay/notify")
    public String notifyPayment(HttpServletRequest request) {
        return alipayService.payNotify(request);
    }
}
