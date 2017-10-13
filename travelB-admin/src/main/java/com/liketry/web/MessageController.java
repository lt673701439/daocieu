package com.liketry.web;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.liketry.domain.Message;
import com.liketry.service.MessageService;
import com.liketry.util.CommonUtils;
import com.liketry.util.Constants;
import com.liketry.util.ShiroUtils;
import com.liketry.util.StringTools;
import com.liketry.web.vm.ResultVM;
import com.liketry.web.vm.SmartPageVM;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * author Simon
 * create 2017/9/4
 * 消息
 */
@Slf4j
@RestController
@RequestMapping("message")
public class MessageController extends BaseController<MessageService, Message> {
    @Override
    @PostMapping("/getSmartData")
    public ResultVM getSmartData(@RequestBody SmartPageVM<Message> spage) {
        log.info("<======列表请求参数json:==" + JSONObject.toJSONString(spage) + "=====>");
        Page<Message> page = new Page<Message>(spage.getPagination().getStart(), spage.getPagination().getNumber());
        if (StringUtils.isBlank(spage.getSort().getPredicate())) {
            spage.getSort().setPredicate("update_time");
        }
        page.setOrderByField(spage.getSort().getPredicate());
        page.setAsc(spage.getSort().getReverse());
        EntityWrapper<Message> wrapper = new EntityWrapper<Message>();
        if (spage.getSearch() != null) {
            Field[] fields = spage.getSearch().getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                try {
                    fields[i].setAccessible(true);
                    Object value = fields[i].get(spage.getSearch());
                    if (null != value && !value.equals("") && !value.equals(0)) {//默认int型数据值为0
                        String fieldname = StringTools.underscoreName(fields[i].getName());
                        wrapper.like(fieldname, value.toString());
                    }
                    fields[i].setAccessible(false);
                } catch (Exception e) {
                }
            }
        }
        wrapper.eq("delflag", "1");
        return ResultVM.ok(service.selectPage(page, wrapper));
    }

    @Override
    @PostMapping
    public ResultVM create(@RequestBody Message msg) {
        if (msg.getMessageType() == null)
            return ResultVM.error(String.valueOf(Constants.data_null));
        msg.setId(CommonUtils.getId());
        msg.setDelflag(1);
        msg.setCreateUserId(ShiroUtils.getUserId());
        msg.setCreateTime(new Date());
        msg.setUpdateTime(new Date());
        msg.setUpdateUserId(ShiroUtils.getUserId());
        if (service.insert(msg)) {
            return ResultVM.ok();
        } else {
            return ResultVM.error();
        }
    }
}