package com.mall.mvcservice.task;


import com.mall.mvcservice.service.PayOrderService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
@Slf4j
public class TimeoutCloseOrderTask {

    @Resource
    private PayOrderService orderService;

    private static final Logger logger = LoggerFactory.getLogger(TimeoutCloseOrderTask.class);

    @Scheduled(cron = "0 0/10 * * * ?") // 每10分钟执行一次
    public void exec() {
        try {
            logger.info("<========== 任务开始: 超时30min后订单自动关闭 ==========>");
            List<String> orderIds = orderService.queryTimeoutOrders();
            if (orderIds == null) {
                logger.info("<========== 任务结束: 没有超时订单 ==========>");
                return;
            }
            for (String orderId : orderIds) {
                boolean status = orderService.changeOrderClose(orderId);
                logger.info("<========== 任务结束: 订单{}关闭状态{} ==========>", orderId, status);
            }
        } catch (Exception e) {
            logger.error("<========== 任务结束: 订单超时关闭异常 ==========>", e);
        }
    }
}
