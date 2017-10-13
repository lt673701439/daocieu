package com.liketry.api;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.liketry.domain.BankCard;
import com.liketry.domain.Message;
import com.liketry.service.MessageService;
import com.liketry.util.CommonUtils;
import com.liketry.util.Constants;
import com.liketry.util.StringTools;
import com.liketry.web.BaseController;
import com.liketry.web.vm.ResultVM;
import com.liketry.web.vm.SmartPageVM;
import com.liketry.web.vm.SmartSort;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * author Simon
 * create 2017/9/4
 * 消息
 */
@RestController
@RequestMapping(value = "api/message", method = RequestMethod.POST)
public class MessageApi extends BaseController<MessageService, Message> {

    @Override
    @PostMapping("getSmartData")
    public ResultVM getSmartData(@RequestBody SmartPageVM<Message> spage) {
        Message message = spage.getSearch();
        if (message.getMessageType() == 2 && (message.getMessageMerchantId() == null || message.getMessageMerchantId().length() != 32))
            return ResultVM.error();
        message.setDelflag(1);
        spage.setSearch(message);
        return super.getSmartData(spage);
    }
//    @Override
//    @PostMapping("getSmartData")
//    public ResultVM getSmartData(@RequestBody SmartPageVM<Message> spage) {
//        Page<Message> page = new Page<Message>(spage.getPagination().getStart(), spage.getPagination().getNumber());
//        if (StringUtils.isBlank(spage.getSort().getPredicate())) {
//            spage.getSort().setPredicate("update_time");
//        }
//        page.setOrderByField(spage.getSort().getPredicate());
//        page.setAsc(spage.getSort().getReverse());
//        EntityWrapper<Message> wrapper = new EntityWrapper<Message>();
//        String messageMerchantId = spage.getSearch().getMessageMerchantId();
//        wrapper.where("delflag = 1 and ( message_type != 2 or (message_type = 2 and message_merchant_id = {0}))", messageMerchantId);
//        return ResultVM.ok(service.selectPage(page, wrapper));
//    }

    @PostMapping("monthData")
    public ResultVM getMonthData(@RequestParam String merchantId) {
        if (StringUtils.isBlank(merchantId))
            return ResultVM.error();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date endDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, -30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startDate = calendar.getTime();
        EntityWrapper<Message> wrapper = new EntityWrapper<Message>();
        wrapper.where("delflag = 1 and ((message_type != 2 and message_publish_time < now()) or (message_type = 2 and message_merchant_id = {0})) and create_time between {1} and {2} ", merchantId, startDate, endDate);
        return ResultVM.ok(service.selectList(wrapper));
    }


    @Override
    @DeleteMapping("/{id}")
    public ResultVM delete(@PathVariable String id) {
        Message message = service.selectById(id);
        if (message == null || message.getMessageType() != 2)
            return ResultVM.error();
        if (service.deleteById(id)) {
            return ResultVM.ok();
        } else {
            return ResultVM.error();
        }
    }
}