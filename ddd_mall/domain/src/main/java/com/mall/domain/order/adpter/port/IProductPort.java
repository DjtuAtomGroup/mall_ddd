package com.mall.domain.order.adpter.port;

import com.mall.domain.order.model.entity.ProductEntity;

public interface IProductPort {


    /**
     * 查询商品信息
     * @param productId
     * @return
     */
    ProductEntity queryProductById(String productId);
}
