package com.mall.infrastruction.gateway;


import com.mall.infrastruction.gateway.dto.ProductDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductRPC {


    @Value("${project.constants.product.name}")
    private String productName;

    @Value("${project.constants.product.desc}")
    private String productDesc;

    @Value("${project.constants.product.price}")
    private String productPrice;

    public ProductDto queryProductById(String productId) {
        return ProductDto.builder()
                .productId(productId)
                .productName(productName)
                .productDesc(productDesc)
                .price(new BigDecimal(productPrice))
                .build();
    };
}
