package com.mall.mvcservice.service;

import com.mall.common.domain.dto.req.CreatePayReqDto;

import javax.servlet.http.HttpServletRequest;

public interface AlipayService {


    /**
     * 创建订单接口
     * @param params
     * @return
     */
    String createPayOrder(CreatePayReqDto params);


    /**
     * 支付结果回调函数
     * @param request
     * @return
     */
    String payNotify(HttpServletRequest request);
}
