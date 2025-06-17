package com.mall.mvcservice.controller;


import com.mall.common.domain.dto.req.ShopCartReqDto;
import com.mall.common.domain.dto.resp.PayOrderRespDto;
import com.mall.common.domain.vo.Result;
import com.mall.mvcservice.service.PayOrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("${mall-mvc.request.prefix}/order")
public class PayOrderController {

    @Resource
    private PayOrderService orderService;

    @GetMapping("/test")
    public Result<String> testController() {
      return Result.success("测试成功");
    };



    @PostMapping("/createOrder")
    public Result<PayOrderRespDto> createPayOrder(
            @RequestParam String userId,
            @RequestParam String productId
    ) {
        ShopCartReqDto shopCartReqDto = ShopCartReqDto.builder()
                .userId(userId)
                .productId(productId)
                .build();
        return Result.success(orderService.createPayOrder(shopCartReqDto));
    }



    @GetMapping("/changeOrderStatus/{orderId}")
    public Result<String> changePayOrderStatus(
            @PathVariable("orderId") String orderId
    ) {
        orderService.changeOrderPayStatus(orderId);
        return Result.success("修改订单状态成功");
    }



    @GetMapping("/queryUnpaidOrder")
    public Result<List<String>> queryUnPaidOrderList() {
        return Result.success(orderService.queryUnPayOrders());
    }



    @GetMapping("/queryTimeoutOrder")
    public Result<List<String>> queryTimeoutOrderList() {
        return Result.success(orderService.queryTimeoutOrders());
    }



    @GetMapping("/closeOrder/{orderId}")
    public Result<Boolean> closeOrder(
            @PathVariable String orderId
    ) {
        return Result.success(orderService.changeOrderClose(orderId));
    }
}
