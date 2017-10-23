package com.liketry.service;

import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * author Simon
 * create 2017/10/11
 * 推送服务
 */
@Service
public interface PushService {
    //发送单个消息消息给固定商户
    boolean addMessage(String merchantId, String content, int actionType);

    //发送广播数据
    boolean sendBroadcastMessage(boolean immediately, Date pushTime, String pushContent, int type, Map<String, Object> extra);

    //发送组播数据
    boolean sendGroupCastMessage(boolean immediately, Date pushTime, String[] selectMerchant, String pushContent, int type, Map<String, Object> extra);
}