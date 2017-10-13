package com.liketry.service.impl;

import com.liketry.service.PushService;
import com.liketry.util.Constants;
import com.liketry.util.PushUtil;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class PushServiceImpl implements PushService {

    @Override
    public boolean sendAddOrderMessage(String merchantId, String time) {
        String device = "ios";
        String body = PushUtil.getSingle(device, "新消息", "您有新的推广订单", merchantId, Constants.PUSH_ORDER);
        PushUtil.UMResultModel data = PushUtil.getUMData(device, body);
        System.out.println(data.toString());
        return data.getStatusCode() == HttpStatus.SC_OK;
    }

    @Override
    public boolean sendPayOrderMessage(String merchantId, String money) {
        String device = "ios";

        String body = PushUtil.getSingle(device, "新消息", "您有新的已支付订单", merchantId, Constants.PUSH_ORDER);
        PushUtil.UMResultModel data = PushUtil.getUMData(device, body);
        return data.getStatusCode() == HttpStatus.SC_OK;
    }

    @Override
    public boolean sendBackOrderMessage(String merchantId, String time) {
        String device = "ios";

        String body = PushUtil.getSingle(device, "新消息", "您有已退单订单", merchantId, Constants.PUSH_ORDER);
        PushUtil.UMResultModel data = PushUtil.getUMData(device, body);
        return data.getStatusCode() == HttpStatus.SC_OK;
    }

    @Override
    public boolean sendRefundMessage(String merchantId, String time) {
        String device = "ios";

        String body = PushUtil.getSingle(device, "新消息", "您有已退款订单", merchantId, Constants.PUSH_ORDER);
        PushUtil.UMResultModel data = PushUtil.getUMData(device, body);
        return data.getStatusCode() == HttpStatus.SC_OK;
    }

    @Override
    public boolean sendEnchashmentMessage(String merchantId, String money) {
        String device = "ios";
        String body = PushUtil.getSingle(device, "新消息", "您的余额有变动", merchantId, Constants.PUSH_ORDER);
        PushUtil.UMResultModel data = PushUtil.getUMData(device, body);
        return data.getStatusCode() == HttpStatus.SC_OK;
    }
}