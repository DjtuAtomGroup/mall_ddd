package com.mall.mvcservice.listener;


import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderPaySuccessListener {


    @Subscribe
    public void eventHandler(String paySuccessMessage) {
        log.info("订单支付成功消息：{}", paySuccessMessage);
    }
}
