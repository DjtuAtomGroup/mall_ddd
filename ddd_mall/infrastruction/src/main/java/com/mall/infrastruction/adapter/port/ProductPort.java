package com.mall.infrastruction.adapter.port;

import com.mall.domain.order.adpter.port.IProductPort;
import com.mall.domain.order.model.entity.ProductEntity;
import com.mall.infrastruction.gateway.ProductRPC;
import com.mall.infrastruction.gateway.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ProductPort implements IProductPort {


    private final ProductRPC productRPC;

    /**
     * 查询商品信息
     *
     * @param productId
     * @return
     */
    @Override
    public ProductEntity queryProductById(String productId) {
        ProductDto product = productRPC.queryProductById(productId);
        return ProductEntity.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .productDesc(product.getProductDesc())
                .price(product.getPrice())
                .build();
    }
}
