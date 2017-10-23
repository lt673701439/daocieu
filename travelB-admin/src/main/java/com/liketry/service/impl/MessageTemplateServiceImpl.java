package com.liketry.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.liketry.domain.MessageTemplate;
import com.liketry.mapper.MessageTemplateMapper;
import com.liketry.service.MessageTemplateService;
import org.springframework.stereotype.Service;

/**
 * author Simon
 * create 2017/10/16
 * 系统消息模板
 */
@Service
public class MessageTemplateServiceImpl extends ServiceImpl<MessageTemplateMapper, MessageTemplate> implements MessageTemplateService {
}
