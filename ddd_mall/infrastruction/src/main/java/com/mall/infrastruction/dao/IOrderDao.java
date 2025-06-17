package com.mall.infrastruction.dao;

import com.mall.infrastruction.dao.po.PayOrder;

import java.util.List;

/**
* @author YJH
* @description 针对表【pay_order】的数据库操作Mapper
* @createDate 2025-06-16 09:53:33
* @Entity generator.domain.PayOrder
*/
public interface IOrderDao {
    int insert(PayOrder order);

    PayOrder queryUnPayOrder(PayOrder order);

    void updateOrderPayInfo(PayOrder order);

    void changeOrderPaySuccess(PayOrder order);

    List<String> queryNoPayNotifyOrder();

    List<String> queryTimeoutCloseOrderList();

    boolean changeOrderClose(String orderId);
}




