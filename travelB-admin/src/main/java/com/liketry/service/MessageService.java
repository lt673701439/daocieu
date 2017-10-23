package com.liketry.service;

import com.baomidou.mybatisplus.service.IService;
import com.liketry.domain.Message;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * author Simon
 * create 2017/9/4
 * 系统消息
 */
@Service
public interface MessageService extends IService<Message> {

    //新增订单
    boolean addOrderMessage(String merchantId, Date time);

    //支付订单
    boolean payOrderMessage(String merchantId, String money);

    //退单
    boolean chargeBackMessage(String merchantId, Date time);

    //退款
    boolean drawbackMessage(String merchantId, Date time, String money);

    //提现
    boolean enchashmentMessage(String merchantId, String money);

    /**
     * 后台创建消息时候，是否发送通用的消息推送
     *
     * @param immediately    是否立即推送
     * @param pushTime       immediately= false 的时候，代表推送的时间
     * @param receiveAll     是否群推
     * @param selectMerchant receiveAll=false时候，代表要推送的商户,多个商户，分割
     * @param pushContent    推送内容
     * @param actionType     推送类别
     * @return 是否成功
     */
    boolean pushMessage(boolean immediately, Date pushTime, boolean receiveAll, String[] selectMerchant, String pushContent, int actionType, Map<String, Object> extra);
}