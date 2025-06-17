package com.mall.domain.order.adpter.repository;

import com.mall.domain.order.model.aggregation.CreateOrderAggregate;
import com.mall.domain.order.model.entity.OrderEntity;
import com.mall.domain.order.model.entity.PayOrderEntity;
import com.mall.domain.order.model.entity.ShopCartEntity;




/**
 * 订单仓储服务 —— domain 领域层就像一个饭点的厨师，他需要的各种材料，米、面、粮、油、水，都不是它生产的，它只是知道要做啥，要用啥，用通过管道【接口】把这些东西传递进来
 */
public interface IOrderRepository {


    /**
     * 查询未支付订单
     * @param params
     * @return
     */
    OrderEntity queryUnpaidOrder(ShopCartEntity params);


    /**
     * 保存订单对象
     * @param params
     */
    void doSaveOrder(CreateOrderAggregate params);


    /**
     * 更新订单支付信息
     * @param params
     */
    void updateOrderPayInfo(PayOrderEntity params);


    /**
     * 订单支付成功回调
     * @param orderId
     */
    void changeOrderPaySuccess(String orderId);
}
