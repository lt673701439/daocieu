package com.liketry.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.liketry.domain.Message;
import com.liketry.mapper.MessageMapper;
import com.liketry.service.MessageService;
import com.liketry.service.PushService;
import com.liketry.util.CommonUtils;
import com.liketry.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * author Simon
 * create 2017/9/4
 * 系统消息
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {
    @Autowired
    private PushService pushService;

    @Override
    public boolean addOrderMessage(String merchantId, Date time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Message msg = new Message();
//        msg.setId(CommonUtils.getId());
//        msg.setMessageType(Constants.MSG_SERVER);
//        msg.setMessageTitle("新消息");
//        msg.setMessageContent("您有新的推广订单，下单时间" + format.format(time));
//        msg.setDelflag(1);
//        msg.setMessagePublishTime(new Date());
//        int size = baseMapper.insert(msg);
        pushService.sendAddOrderMessage(merchantId, format.format(time));
//        return size > 0;
        return true;
    }

    @Override
    public boolean payOrderMessage(String merchantId, String money) {
        return false;
    }

    @Override
    public boolean backOrderMessage(String merchantId, Date time) {
        return false;
    }

    @Override
    public boolean refundMessage(String merchantId, Date time) {
        return false;
    }

    @Override
    public boolean enchashmentMessage(String merchantId, String money) {
        return false;
    }
}