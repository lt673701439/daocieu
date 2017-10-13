package com.liketry.service;

import org.springframework.stereotype.Service;
/**
 * author Simon
 * create 2017/10/11
 * 推送服务
 */
@Service
public interface PushService {
    //新增订单
    boolean sendAddOrderMessage(String merchantId, String time);

    //支付订单
    boolean sendPayOrderMessage(String merchantId, String money);

    //退单
    boolean sendBackOrderMessage(String merchantId, String time);

    //退款
    boolean sendRefundMessage(String merchantId, String time);

    //提现
    boolean sendEnchashmentMessage(String merchantId, String money);
}