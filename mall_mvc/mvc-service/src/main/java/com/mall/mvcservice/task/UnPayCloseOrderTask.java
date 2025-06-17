package com.mall.mvcservice.task;


import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.mall.mvcservice.service.PayOrderService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
@Slf4j
public class UnPayCloseOrderTask {

    @Resource
    private PayOrderService orderService;

    @Resource
    private AlipayClient alipayClient;

    private static final Logger logger = LoggerFactory.getLogger(UnPayCloseOrderTask.class);

    @Scheduled(cron = "0/3 * * * * ?") // 每3秒执行一次
    public void exec() {
        try {
            logger.info("<========== 开始执行定时任务: 检测未接收到或未正确处理的支付回调通知 ==========>");
            List<String> orderIds = orderService.queryUnPayOrders();
            if (orderIds == null) {
                logger.info("<========== 未检测到未支付订单 ==========>");
                return;
            }
            for (String orderId : orderIds) {
                AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
                AlipayTradeQueryModel bizModel = new AlipayTradeQueryModel();
                bizModel.setOutTradeNo(orderId);
                request.setBizModel(bizModel);

                AlipayTradeQueryResponse response = alipayClient.execute(request);
                String code = response.getCode();
                if (code.equals("10000")) {
                    orderService.changeOrderPayStatus(orderId);
                    logger.info("<========== 订单支付成功 ==========>");
                }
            }
        } catch (Exception e) {
            logger.error("<========== 定时任务执行异常 ==========>", e);
        }
    }
}
