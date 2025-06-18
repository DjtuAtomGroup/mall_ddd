package com.mall.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.common.domain.po.PayOrder;

import java.util.List;

/**
* @author YJH
* @description 针对表【pay_order】的数据库操作Mapper
* @createDate 2025-06-16 09:53:33
* @Entity generator.domain.PayOrder
*/
public interface PayOrderMapper extends BaseMapper<PayOrder> {
    int insert(PayOrder order);

    PayOrder queryUnPayOrder(PayOrder order);

    void updateOrderPayInfo(PayOrder order);

    void changeOrderPaySuccess(PayOrder order);

    List<String> queryNoPayNotifyOrder();

    List<String> queryTimeoutCloseOrderList();

    boolean changeOrderClose(String orderId);
}




