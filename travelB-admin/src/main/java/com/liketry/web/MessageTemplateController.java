package com.liketry.web;

import com.liketry.domain.Message;
import com.liketry.domain.MessageTemplate;
import com.liketry.service.MessageService;
import com.liketry.service.MessageTemplateService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author Simon
 * create 2017/10/16
 * 系统消息模板
 */
@Slf4j
@RestController
@RequestMapping("messageTemplate")
@Api(value = "系统消息模板", description = "供平台管理调用")
public class MessageTemplateController extends BaseController<MessageTemplateService, MessageTemplate> {

}
