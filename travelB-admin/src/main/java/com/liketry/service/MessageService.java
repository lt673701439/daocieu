package com.liketry.service;

import com.baomidou.mybatisplus.service.IService;
import com.liketry.domain.Message;
import org.springframework.stereotype.Service;

import java.util.Date;

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
    boolean backOrderMessage(String merchantId, Date time);

    //退款
    boolean refundMessage(String merchantId, Date time);

    //提现
    boolean enchashmentMessage(String merchantId, String money);
}