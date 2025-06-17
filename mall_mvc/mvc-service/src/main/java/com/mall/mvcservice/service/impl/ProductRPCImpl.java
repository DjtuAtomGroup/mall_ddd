package com.mall.mvcservice.service.impl;

import com.mall.common.domain.vo.ProductVo;
import com.mall.mvcservice.service.ProductRPC;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class ProductRPCImpl implements ProductRPC {

    @Override
    public ProductVo queryProductById(String productId) {
        return ProductVo.builder()
                .productId(productId)
                .productDesc("这是一个测试商品")
                .productName("测试商品")
                .price(new BigDecimal("16.8"))
                .build();
    }
}
