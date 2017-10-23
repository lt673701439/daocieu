package com.liketry.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.liketry.domain.Message;
import com.liketry.domain.MessageTemplate;
import com.liketry.mapper.MessageMapper;
import com.liketry.service.MessageService;
import com.liketry.service.MessageTemplateService;
import com.liketry.service.PushService;
import com.liketry.util.CommonUtils;
import com.liketry.util.Constants;
import com.liketry.util.FormatUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * author Simon
 * create 2017/9/4
 * 系统消息
 */
@Slf4j
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {
    private final PushService pushService;
    private final MessageTemplateService templateService;

    @Autowired
    public MessageServiceImpl(PushService pushService, MessageTemplateService templateService) {
        this.pushService = pushService;
        this.templateService = templateService;
    }

    @Override
    public boolean chargeBackMessage(String merchantId, Date time) {
        log.info("Message server : chargeBackMessage merchantId= " + merchantId + "time = " + time);
        String orderTime = FormatUtils.getCommonDateStr(time);
        EntityWrapper<MessageTemplate> wrapper = new EntityWrapper<>();
        wrapper.eq("msg_template_type", 2);
        MessageTemplate template = templateService.selectOne(wrapper);
        String content = template.getMsgTemplateContent().replace("@returntime@", orderTime);
        Message msg = new Message();
        msg.setId(CommonUtils.getId());
        msg.setMessageType(Constants.MSG_SERVER);
        msg.setMessageTitle("新消息");
        msg.setMessageContent(content);
        msg.setDelflag(1);
        msg.setMessagePublishTime(new Date());
        msg.setMessageMerchantId(merchantId);
        int size = baseMapper.insert(msg);
        pushService.addMessage(merchantId, content, Constants.PUSH_ORDER_BACK);
        return size > 0;
    }

    @Override
    public boolean drawbackMessage(String merchantId, Date time, String money) {
        log.info("Message server : drawbackMessage merchantId= " + merchantId + "time = " + time + " money=" + money);
        EntityWrapper<MessageTemplate> wrapper = new EntityWrapper<>();
        wrapper.eq("msg_template_type", 3);
        MessageTemplate template = templateService.selectOne(wrapper);
        String content = template.getMsgTemplateContent().replace("@returnmoney@", money);
        Message msg = new Message();
        msg.setId(CommonUtils.getId());
        msg.setMessageType(Constants.MSG_SERVER);
        msg.setMessageTitle("新消息");
        msg.setMessageContent(content);
        msg.setDelflag(1);
        msg.setMessagePublishTime(new Date());
        msg.setMessageMerchantId(merchantId);
        int size = baseMapper.insert(msg);
        pushService.addMessage(merchantId, content, Constants.PUSH_ORDER_DRAWBACK);
        return size > 0;
    }

    @Override
    public boolean addOrderMessage(String merchantId, Date time) {
        log.info("Message server : addOrderMessage merchantId= " + merchantId + "time = " + time);
        String orderTime = FormatUtils.getCommonDateStr(time);
        EntityWrapper<MessageTemplate> wrapper = new EntityWrapper<>();
        wrapper.eq("msg_template_type", 0);
        MessageTemplate template = templateService.selectOne(wrapper);
        String msgContent = template.getMsgTemplateContent().replace("@time@", orderTime);
        if (StringUtils.isEmpty(msgContent)) {
            log.error("addOrderMessage error: msg_template_type= 0,merchantId=" + merchantId + "time=" + time);
            return false;
        }
        Message msg = new Message();
        msg.setId(CommonUtils.getId());
        msg.setMessageType(Constants.MSG_SERVER);
        msg.setMessageTitle("新消息");
        msg.setMessageContent(msgContent);
        msg.setDelflag(1);
        msg.setMessageMerchantId(merchantId);
        msg.setMessagePublishTime(new Date());
        int size = baseMapper.insert(msg);
        pushService.addMessage(merchantId, msgContent, Constants.PUSH_ORDER_ADD);
        return size > 0;
    }

    @Override
    public boolean payOrderMessage(String merchantId, String money) {
        log.info("Message server : payOrderMessage merchantId= " + merchantId + " money=" + money);
        EntityWrapper<MessageTemplate> wrapper = new EntityWrapper<>();
        wrapper.eq("msg_template_type", 1);
        MessageTemplate template = templateService.selectOne(wrapper);
        String content = template.getMsgTemplateContent().replace("@money@", money);
        Message msg = new Message();
        msg.setId(CommonUtils.getId());
        msg.setMessageType(Constants.MSG_SERVER);
        msg.setMessageTitle("新消息");
        msg.setMessageContent(content);
        msg.setDelflag(1);
        msg.setMessageMerchantId(merchantId);
        msg.setMessagePublishTime(new Date());
        int size = baseMapper.insert(msg);
        pushService.addMessage(merchantId, content, Constants.PUSH_ORDER_PAY);
        return size > 0;
    }

    @Override
    public boolean enchashmentMessage(String merchantId, String money) {
        log.info("Message server : enchashmentMessage merchantId= " + merchantId + " money=" + money);
        EntityWrapper<MessageTemplate> wrapper = new EntityWrapper<>();
        wrapper.eq("msg_template_type", 4);
        MessageTemplate template = templateService.selectOne(wrapper);
        String content = template.getMsgTemplateContent().replace("@money@", money);
        Message msg = new Message();
        msg.setId(CommonUtils.getId());
        msg.setMessageType(Constants.MSG_SERVER);
        msg.setMessageTitle("新消息");
        msg.setMessageContent(content);
        msg.setDelflag(1);
        msg.setMessageMerchantId(merchantId);
        msg.setMessagePublishTime(new Date());
        int size = baseMapper.insert(msg);
        pushService.addMessage(merchantId, content, Constants.PUSH_ORDER_ENCHASHMENT);
        return size > 0;
    }

    @Override
    public boolean pushMessage(boolean immediately, Date pushTime, boolean receiveAll, String[] selectMerchant, String pushContent, int actionType, Map<String, Object> extra) {
        if (receiveAll) {//全体推送
            return pushService.sendBroadcastMessage(immediately, pushTime, pushContent, actionType, extra);
        } else {
            return pushService.sendGroupCastMessage(immediately, pushTime, selectMerchant, pushContent, actionType, extra);
        }
    }
}