package com.mall.mvcservice.service;

import com.mall.common.domain.vo.ProductVo;

public interface ProductRPC {

    ProductVo queryProductById(String productId);
}
