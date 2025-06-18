package com.mall.api.service;

import com.mall.api.dto.req.CreatePayReqDto;

public interface PayService {


    /**
     * 创建订单支付
     * @param params
     * @return
     */
    String createPayOrder(CreatePayReqDto params);
}
