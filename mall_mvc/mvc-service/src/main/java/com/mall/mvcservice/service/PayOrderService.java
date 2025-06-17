package com.mall.mvcservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.common.domain.dto.req.ShopCartReqDto;
import com.mall.common.domain.dto.resp.PayOrderRespDto;
import com.mall.common.domain.po.PayOrder;

import java.util.List;

/**
* @author YJH
* @description 针对表【pay_order】的数据库操作Service
* @createDate 2025-06-16 09:53:33
*/
public interface PayOrderService extends IService<PayOrder> {


    PayOrderRespDto createPayOrder(ShopCartReqDto params);

    void changeOrderPayStatus(String orderId);

    List<String> queryUnPayOrders();

    List<String> queryTimeoutOrders();

    boolean changeOrderClose(String orderId);
}
