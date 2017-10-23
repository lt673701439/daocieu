package com.liketry.service.impl;

import com.liketry.service.MerChantService;
import com.liketry.service.PushService;
import com.liketry.util.Constants;
import com.liketry.util.PushUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class PushServiceImpl implements PushService {
    private final MerChantService merChantService;

    @Autowired
    public PushServiceImpl(MerChantService merChantService) {
        this.merChantService = merChantService;
    }


    @Override
    public boolean addMessage(String merchantId, String content, int actionType) {
        String device = merChantService.selectByDevice(merchantId);
        if (StringUtils.isEmpty(device)) {
            log.error("push addMessage error: device not fund, merchantId=" + merchantId + "content=" + content);
            return false;
        }
        String body = PushUtil.getSingle(device, "新消息", content, merchantId, actionType);
        if (StringUtils.isEmpty(body)) {
            log.error("push addMessage error: body is empty");
            return false;
        }
        PushUtil.UMResultModel data = PushUtil.getUMData(device, body);
        log.info(data.toString());
        return data.getStatusCode() == HttpStatus.SC_OK;
    }

    //广播
    @Override
    public boolean sendBroadcastMessage(boolean immediately, Date pushTime, String pushContent, int actionType, Map<String, Object> extra) {
        String body = PushUtil.getIOSBroadcastData("新消息", immediately, pushTime, pushContent, actionType, extra);
        if (StringUtils.isEmpty(body)) {
            log.error("push sendBroadcastMessage error: body is empty for iso");
            return false;
        }
        PushUtil.UMResultModel isoData = PushUtil.getUMData(Constants.DEVICE_IOS, body);
        log.info(isoData.toString());
        body = PushUtil.getAndroidBroadcastData("新消息", immediately, pushTime, pushContent, actionType, extra);
        if (StringUtils.isEmpty(body)) {
            log.error("push sendBroadcastMessage error: body is empty for android");
            return false;
        }
        PushUtil.UMResultModel androidData = PushUtil.getUMData(Constants.DEVICE_ANDROID, body);
        log.info(androidData.toString());
        return isoData.getStatusCode() == HttpStatus.SC_OK && androidData.getStatusCode() == HttpStatus.SC_OK;
    }

    //组播
    @Override
    public boolean sendGroupCastMessage(boolean immediately, Date pushTime, String[] selectMerchant, String pushContent, int type, Map<String, Object> extra) {
        if (selectMerchant == null || selectMerchant.length < 1)
            return false;
        List<String> iosMerchant = new ArrayList<>();
        List<String> androidMerchant = new ArrayList<>();
        for (String id : selectMerchant) {
            String device = merChantService.selectByDevice(id);
            if (Constants.DEVICE_IOS.equals(device)) {
                iosMerchant.add(id);
            } else if (Constants.DEVICE_ANDROID.equals(device)) {
                androidMerchant.add(id);
            }
        }
        boolean iosSendStatus = true;
        boolean androidSendStatus = true;
        if (iosMerchant.size() > 0) {
            int size = iosMerchant.size() / Constants.MAX_ALIAS + (iosMerchant.size() % Constants.MAX_ALIAS > 0 ? 1 : 0);
            log.info("push umeng groupcast message: device = ios, size= " + size);
            for (int i = 0; i < size; i++) {
                List<String> item;
                if (i < size - 1)
                    item = iosMerchant.subList(i * Constants.MAX_ALIAS, (i + 1) * Constants.MAX_ALIAS);
                else
                    item = iosMerchant.subList(i * Constants.MAX_ALIAS, iosMerchant.size());
                String body = PushUtil.getIOSGroupCastData("新消息", immediately, pushTime, pushContent, type, StringUtils.join(item, ","), extra);
                PushUtil.UMResultModel isoData = PushUtil.getUMData(Constants.DEVICE_IOS, body);
                iosSendStatus = isoData.getStatusCode() == HttpStatus.SC_OK && iosSendStatus;
                log.info("push umeng groupcast message: device = ios, item = " + i + " result=" + isoData.toString());
            }
        }
        if (androidMerchant.size() > 0) {
            int size = androidMerchant.size() / Constants.MAX_ALIAS + (androidMerchant.size() % Constants.MAX_ALIAS > 0 ? 1 : 0);
            log.info("push umeng groupcast message: device = android, size= " + size);
            for (int i = 0; i < size; i++) {
                List<String> item;
                if (i < size - 1)
                    item = androidMerchant.subList(i * Constants.MAX_ALIAS, (i + 1) * Constants.MAX_ALIAS);
                else
                    item = androidMerchant.subList(i * Constants.MAX_ALIAS, androidMerchant.size());
                String body = PushUtil.getAndroidGroupCastData("新消息", immediately, pushTime, pushContent, type, StringUtils.join(item, ","), extra);
                PushUtil.UMResultModel androidData = PushUtil.getUMData(Constants.DEVICE_ANDROID, body);
                androidSendStatus = androidData.getStatusCode() == HttpStatus.SC_OK && androidSendStatus;
                log.info("push umeng groupcast message: device = android, item = " + i + " result=" + androidData.toString());
            }
        }
        return iosSendStatus && androidSendStatus;
    }
}