package com.mall.triggers.http;


import com.mall.api.response.Response;
import com.mall.domain.order.service.PayOrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("${project.config.request.prefix}/pay")
public class AlipayController {


    @Resource
    private PayOrderService orderService;


    @PostMapping("/createPayOrder")
    public Response<String> createPayOrder() {
        return Response.success("success");
    }

}
