package com.mall.mvcservice.service.impl;

import com.alipay.api.internal.util.AlipaySignature;
import com.google.protobuf.ServiceException;
import com.mall.common.domain.dto.req.CreatePayReqDto;
import com.mall.common.domain.dto.req.ShopCartReqDto;
import com.mall.common.domain.dto.resp.PayOrderRespDto;
import com.mall.mvcservice.service.AlipayService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j
public class AlipayServiceImpl implements AlipayService {


    @Value("${alipay.alipay_public_key}")
    private String alipayPublicKey;

    @Resource
    private PayOrderServiceImpl orderService;



    /**
     * 创建订单接口
     *
     * @param params
     * @return
     */
    @Override
    @SneakyThrows
    public String createPayOrder(CreatePayReqDto params) {
        try {
            log.info("商品下单， 根据商品ID创建支付单开始， userId: {}, productId: {}", params.getUserId(), params.getProductId());
            String userId = params.getUserId();
            String productId = params.getProductId();

            ShopCartReqDto shopCartReqDto = ShopCartReqDto.builder()
                    .userId(userId)
                    .productId(productId)
                    .build();
            PayOrderRespDto orderRespDto = orderService.createPayOrder(shopCartReqDto);
            log.info("根据商品ID创建支付单完成， userId: {}, productId: {}", userId, productId);
            return orderRespDto.getPayUrl();
        } catch (Exception e) {
            log.error("商品下单， 根据商品ID创建支付单异常， userId: {}, productId: {}", params.getUserId(), params.getProductId());
            throw new ServiceException("创建订单异常");
        }
    }

    /**
     * 支付结果回调函数
     *
     * @param request
     * @return
     */
    @Override
    @SneakyThrows
    public String payNotify(HttpServletRequest request) {
        try {
            log.info("支付回调， 消息接收： {}", request.getParameter("trade_status"));

            if (!request.getParameter("trade_status").equals("TRADE_SUCCESS")) {
                return "false";
            }

            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                params.put(name, request.getParameter(name));
            }

            String tradeNo = params.get("out_trade_no");
            String gmtPayment = params.get("gmt_payment");
            String alipayTradeNo = params.get("trade_no");
            String sign = params.get("sign");
            String content = AlipaySignature.getSignCheckContentV1(params);
            // 验证签名
            boolean checkSignature = AlipaySignature.rsa256CheckContent(content, sign, alipayPublicKey, "UTF-8");

            if (!checkSignature) {
                return "false";
            }

            // 验签通过
            log.info("支付回调，交易名称: {}", params.get("subject"));
            log.info("支付回调，交易状态: {}", params.get("trade_status"));
            log.info("支付回调，支付宝交易凭证号: {}", params.get("trade_no"));
            log.info("支付回调，商户订单号: {}", params.get("out_trade_no"));
            log.info("支付回调，交易金额: {}", params.get("total_amount"));
            log.info("支付回调，买家在支付宝唯一id: {}", params.get("buyer_id"));
            log.info("支付回调，买家付款时间: {}", params.get("gmt_payment"));
            log.info("支付回调，买家付款金额: {}", params.get("buyer_pay_amount"));
            log.info("支付回调，支付回调，更新订单 {}", tradeNo);

            orderService.changeOrderPayStatus(tradeNo);

            return "success";
        } catch (Exception e) {
            log.error("支付回调消息接收失败", e);
            throw new ServiceException("支付回调消息接收失败");
        }
    }
}
