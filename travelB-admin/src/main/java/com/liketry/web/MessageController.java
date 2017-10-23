package com.liketry.web;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.liketry.domain.Merchant;
import com.liketry.domain.Message;
import com.liketry.domain.Promotion;
import com.liketry.service.MerChantService;
import com.liketry.service.MessageService;
import com.liketry.service.PromotionService;
import com.liketry.util.*;
import com.liketry.web.vm.ResultVM;
import com.liketry.web.vm.SmartPageVM;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.apache.bcel.classfile.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;

/**
 * author Simon
 * create 2017/9/4
 * 消息
 */
@Slf4j
@RestController
@RequestMapping("message")
public class MessageController extends BaseController<MessageService, Message> {
    private int ERROR_MSG_ID_EMPTY = 1001;
    private int ERROR_DATA_EMPTY = 1002;
    private int ERROR_TYPE_FAILED = 1003;
    private final MerChantService merChantService;
    final PromotionService promotionService;

    @Autowired
    public MessageController(MerChantService merChantService, PromotionService promotionService) {
        this.merChantService = merChantService;
        this.promotionService = promotionService;
    }

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
            return ResultVM.error(Constants.data_null, "消息类型为空");
        msg.setId(CommonUtils.getId());
        msg.setDelflag(1);
        msg.setCreateUserId(ShiroUtils.getUserId());
        msg.setCreateTime(new Date());
        msg.setUpdateTime(new Date());
        msg.setUpdateUserId(ShiroUtils.getUserId());
        if (msg.getMessageIsPush() != null && msg.getMessageIsPush()) {
            boolean immediately = msg.getMessageImmediatelyPush();
            Date pushTime = immediately ? null : msg.getMessagePublishTime();
            boolean receiveAll = msg.getMessageReceiveAll();
            String[] selectMerchant = receiveAll ? null : msg.getMessageMerchantId().split(",");
            int actionType = 0;
            HashMap<String, Object> extra = null;
            switch (msg.getMessageType()) {
                case Constants.MSG_NOTICE:
                    actionType = Constants.PUSH_NOTICE;
                    break;
                case Constants.MSG_ACTION:
                    actionType = Constants.PUSH_ACTION;
                    extra = new HashMap<>();
                    extra.put("promotion_id", msg.getId());
                    break;
                case Constants.MSG_SERVER:
                    actionType = Constants.PUSH_SERVER;
                    break;
            }
            service.pushMessage(immediately, pushTime, receiveAll, selectMerchant, msg.getMessageContent(), actionType, extra);
        }
        if (msg.getMessageType() != null && msg.getMessageType() == 1 && msg.getMessageIsSms()) {//发短信
            boolean receiveAll = msg.getMessageReceiveAll();
            String[] selectMerchant = receiveAll ? null : msg.getMessageMerchantId().split(",");
            String[] mobiles = merChantService.selectByMobile(receiveAll, selectMerchant);
            if (mobiles != null && mobiles.length > 0) {
                Promotion promotion = promotionService.selectById(msg.getMessagePromotionId());
                if (promotion != null) {
                    String title = promotion.getPromotionName();
                    String date = FormatUtils.getChineseDate(promotion.getStartTime()) + "至" + FormatUtils.getChineseDate(promotion.getStartTime());
                    for (String ms : mobiles) {
                        SmsUtils.getInstance().sendMessageMsg(ms, title, date);
                    }
                } else {
                    log.info("send sms error: promotion not find by " + msg.getMessagePromotionId());
                }
            }
        }

        if (service.insert(msg)) {
            return ResultVM.ok();
        } else {
            return ResultVM.error();
        }
    }

    @ApiOperation(value = "上传消息关联图片", notes = "上传消息关联图片")
    @RequestMapping(value = "putImg", method = RequestMethod.POST)
    public ResultVM putImg(@RequestParam MultipartFile img) {
        String suffix = img.getOriginalFilename();
        if (StringUtils.isBlank(suffix) || suffix.split("\\.").length < 2)
            return ResultVM.error(ERROR_TYPE_FAILED, "不支持的图片格式");
        String[] split = suffix.split("\\.");
        suffix = split[split.length - 1].toLowerCase();
        if (!"jpg".equals(suffix) && !"jpeg".equals(suffix) && !"png".equals(suffix))
            return ResultVM.error(ERROR_TYPE_FAILED, "不支持的图片格式");
        String path = FileUtils.getPathAndCreate(Constants.ACTIVITY_LOCAL, suffix);
        boolean saveStatus = FileUtils.saveFile(path, img);
        log.info("msg_path: " + path + ",status: " + saveStatus);
        if (saveStatus)
            return ResultVM.ok(path);
        else
            return ResultVM.error(Constants.ERROR_INSERT_FAILED, "数据插入失败");
    }
}