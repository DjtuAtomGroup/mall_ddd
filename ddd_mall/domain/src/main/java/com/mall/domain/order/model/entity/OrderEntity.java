package com.mall.domain.order.model.entity;


import com.mall.domain.order.model.valobj.OrderStatusVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {

    /** 商品ID */
    private String productId;
    /** 商品 */
    private String productName;
    /** 订单编号 */
    private String orderId;
    /** 下单时间 */
    private Date orderTime;
    /** 订单状态；create-创建完成、pay_wait-等待支付、pay_success-支付成功、deal_done-交易完成、close-订单关单 */
    private OrderStatusVo orderStatus;
    /** 订单金额 */
    private BigDecimal totalAmount;
    /** 支付信息 */
    private String payUrl;
}
