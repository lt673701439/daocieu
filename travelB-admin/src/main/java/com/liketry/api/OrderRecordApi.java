package com.liketry.api;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.liketry.domain.Merchant;
import com.liketry.domain.OrderRecord;
import com.liketry.service.CodeService;
import com.liketry.service.MerChantService;
import com.liketry.service.MessageService;
import com.liketry.service.OrderRecordService;
import com.liketry.util.CommonUtils;
import com.liketry.util.Constants;
import com.liketry.web.BaseController;
import com.liketry.web.vm.ResultVM;
import com.liketry.web.vm.SmartPageVM;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping("api/orderApi")
public class OrderRecordApi extends BaseController<OrderRecordService, OrderRecord> {
    private final int ERROR_NOT_FIND_MERCHANT = 1001;

    private final CodeService codeService;
    private final MerChantService merChantService;
    private final MessageService messageService;

    @Autowired
    public OrderRecordApi(MerChantService merChantService, CodeService codeService, MessageService messageService) {
        this.merChantService = merChantService;
        this.codeService = codeService;
        this.messageService = messageService;
    }

    @PostMapping("/getSmartData")
    public ResultVM getSmartData(@RequestBody SmartPageVM<OrderRecord> spage) {
        log.info("<======列表请求参数json:==" + JSONObject.toJSONString(spage) + "=====>");
        String startSearch = spage.getStartSearch();
        String endSearch = spage.getEndSearch();
        String merchantId = spage.getSearch().getOrMerchantId();
        if (StringUtils.isEmpty(merchantId))
            ResultVM.error();
        Page<OrderRecord> page = new Page<OrderRecord>(spage.getPagination().getStart(), spage.getPagination().getNumber());
        if (StringUtils.isBlank(spage.getSort().getPredicate()))
            spage.getSort().setPredicate("update_time");
        page.setOrderByField(spage.getSort().getPredicate());
        page.setAsc(spage.getSort().getReverse());
        EntityWrapper<OrderRecord> wrapper = new EntityWrapper<OrderRecord>();
        wrapper.setSqlSelect("id, or_code, or_merchant_id, or_source_id, or_source_code, or_status, or_user_id, or_user_name, or_mobile, or_order_time, or_pay_time, or_back_time, or_refund_time,   IFNULL(or_pay_price,0) as or_pay_price, IFNULL(or_merchant_gain,0) as or_merchant_gain, IFNULL(or_refund_price,0) as or_refund_price, IFNULL(or_merchant_back_price,0) as or_merchant_back_price, IFNULL(or_balance,0) as or_balance, or_info, create_user_id, create_time, update_user_id, update_time, or_type, transaction_no, or_user_icon");
//        wrapper.where("create_time between STR_TO_DATE({0},'%Y-%m-%d %H:%i:%s') and STR_TO_DATE( {1} ,'%Y-%m-%d %H:%i:%s')", startSearch, endSearch);
        wrapper.between("create_time", startSearch, endSearch);
        wrapper.eq("or_merchant_id", merchantId);
        float total = service.generalIncome(merchantId, startSearch, endSearch);
        Page<OrderRecord> data = service.selectPage(page, wrapper);
        ResultVM ok = ResultVM.ok(data);
        ok.setExtra(total);
        return ok;
    }


    //创建
    @PostMapping
    public ResultVM create(@RequestBody OrderRecord order) {
        log.info("receive: create order parameter =  " + order.toString());
        if (!insertValidate(order))
            return ResultVM.error(String.valueOf(Constants.ERROR_DATA_NOT_COMPLETE));
        Merchant merchant = merChantService.selectById(order.getOrMerchantId());
        if (merchant == null)
            return  ResultVM.error(String.valueOf(ERROR_NOT_FIND_MERCHANT));
        order.setOrBalance(merchant.getMerchantBalance());
        order.setId(CommonUtils.getId());
        order.setOrCode(codeService.getBODCode(merchant.getMerchantCode()));
        boolean insert = service.insert(order);
        if (insert) {
            messageService.addOrderMessage(order.getOrMerchantId(), order.getOrOrderTime());
        }
        return insert ? ResultVM.ok(order) : ResultVM.error(String.valueOf(Constants.ERROR_INSERT_FAILED));
    }

    //三分钟失效订单
    @PostMapping("back")
    public ResultVM back(@RequestBody OrderRecord order) {
        log.info("receive: back order parameter =  " + order.toString());
        if (!backValidate(order))
            return ResultVM.error(String.valueOf(Constants.ERROR_DATA_NOT_COMPLETE));
        EntityWrapper<OrderRecord> entity = new EntityWrapper<OrderRecord>();
        entity.where("or_source_id = {0} and or_source_code = {1}", order.getOrSourceId(), order.getOrSourceCode());
        OrderRecord sqlOrder = service.selectOne(entity);
        if (sqlOrder == null)
            return ResultVM.error(String.valueOf(Constants.ERROR_DATA_EMPTY));
        sqlOrder.setOrStatus(4);
        sqlOrder.setOrBackTime(new Date());
        boolean insert = service.updateById(sqlOrder);
        return insert ? ResultVM.ok() : ResultVM.error(String.valueOf(Constants.ERROR_INSERT_FAILED));
    }

    //后台手动退单
    @PostMapping("chargeBack")
    public ResultVM chargeBack(@RequestBody OrderRecord order) {
        log.info("receive: chargeBack order parameter =  " + order.toString());
        if (!backValidate(order))
            return ResultVM.error(String.valueOf(Constants.ERROR_DATA_NOT_COMPLETE));
        EntityWrapper<OrderRecord> entity = new EntityWrapper<OrderRecord>();
        entity.where("or_source_id = {0} and or_source_code = {1}", order.getOrSourceId(), order.getOrSourceCode());
        OrderRecord sqlOrder = service.selectOne(entity);
        if (sqlOrder == null)
            return ResultVM.error(String.valueOf(Constants.ERROR_DATA_EMPTY));
        sqlOrder.setOrStatus(5);
        sqlOrder.setOrBackTime(new Date());
        boolean insert = service.updateById(sqlOrder);
        if (insert) {
            messageService.chargeBackMessage(sqlOrder.getOrMerchantId(), sqlOrder.getOrOrderTime());
        }
        return insert ? ResultVM.ok() : ResultVM.error(String.valueOf(Constants.ERROR_INSERT_FAILED));
    }


    private boolean insertValidate(OrderRecord order) {
        if (order == null)
            return false;
        if (StringUtils.isBlank(order.getOrSourceId()) || StringUtils.isBlank(order.getOrSourceCode()))
            return false;
        if (StringUtils.isBlank(order.getOrMerchantId()))
            return false;
        if (order.getOrType() == null || order.getOrStatus() == null || order.getOrStatus() != 1)
            return false;
        if (StringUtils.isBlank(order.getOrUserId()) || StringUtils.isBlank(order.getOrUserName()))
            return false;
        if (order.getOrOrderTime() == null)
            return false;
        return true;
    }

    //退单验证
    private boolean backValidate(OrderRecord order) {
        if (order == null || StringUtils.isBlank(order.getOrSourceId()) || StringUtils.isBlank(order.getOrSourceCode()))
            return false;
        if (order.getOrType() == null)
            return false;
        //if (order.getOrBackTime() == null)
        //    return false;
        return true;
    }

    @PostMapping("update")
    public ResultVM updateOrderRecord(@RequestBody OrderRecord order) {
        order.setUpdateTime(new Date());
        boolean updateStatus = service.updateById(order);
        if (updateStatus) {
            log.info("order_record_update_success--->" + order.toString());
            return ResultVM.ok();
        } else {
            log.error("order_record_update_error--->" + order.toString());
            return ResultVM.error();
        }
    }

    @PutMapping
    public ResultVM update(@RequestBody OrderRecord t) {
        return ResultVM.error();
    }
}