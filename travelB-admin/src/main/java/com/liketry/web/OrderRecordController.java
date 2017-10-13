package com.liketry.web;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.liketry.domain.Merchant;
import com.liketry.domain.OrderRecord;
import com.liketry.service.CodeService;
import com.liketry.service.MerChantService;
import com.liketry.service.OrderRecordService;
import com.liketry.util.CommonUtils;
import com.liketry.util.Constants;
import com.liketry.util.StringTools;
import com.liketry.web.vm.ResultVM;
import com.liketry.web.vm.SmartPageVM;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;

@Slf4j
@RestController
@RequestMapping("orderRecord")
public class OrderRecordController extends BaseController<OrderRecordService, OrderRecord> {
    private final MerChantService merChantService;

    @Autowired
    public OrderRecordController(MerChantService merChantService) {
        this.merChantService = merChantService;
    }


//    @PostMapping("/getSmartData")
//    public ResultVM getSmartData(@RequestBody SmartPageVM<OrderRecord> spage) {
//        log.info("<======列表请求参数json:==" + JSONObject.toJSONString(spage) + "=====>");
//
//        Page<OrderRecord> page = new Page<>(spage.getPagination().getStart(), spage.getPagination().getNumber());
//        if (StringUtils.isBlank(spage.getSort().getPredicate())) {
//            spage.getSort().setPredicate("update_time");
//        }
//
//        page.setOrderByField(spage.getSort().getPredicate());
//        page.setAsc(spage.getSort().getReverse());
//        EntityWrapper<OrderRecord> wrapper = new EntityWrapper<>();
//
//        if (spage.getSearch() != null) {
//            Field[] fields = spage.getSearch().getClass().getDeclaredFields();
//            for (int i = 0; i < fields.length; i++) {
//                try {
//                    fields[i].setAccessible(true);
//                    Object value = fields[i].get(spage.getSearch());
//                    if (null != value && !value.equals("") && !value.equals(0)) {//默认int型数据值为0
//                        String fieldname = StringTools.underscoreName(fields[i].getName());
//                        wrapper.like(fieldname, value.toString());
//                    }
//                    fields[i].setAccessible(false);
//                } catch (Exception e) {
//                }
//            }
//        }
//        Merchant merchant = merChantService.selectById(spage.getSearch().getOrMerchantId());
//        if (merchant == null) {
//            return ResultVM.error(Constants.ERROR_DATA_EMPTY, null);
//        }
//        String shopName = merchant.getMerchantShopname() == null ? "" : merchant.getMerchantShopname();
//        wrapper.setSqlSelect("id, or_code, or_merchant_id, or_source_id, or_source_code, or_status, or_user_id, or_user_name, or_mobile, or_order_time, or_pay_time, or_back_time, or_refund_time, or_pay_price, or_merchant_gain, or_refund_price, or_merchant_back_price, or_balance, or_info, create_user_id, create_time, update_user_id, update_time, or_type, transaction_no, or_user_icon, '" + shopName + "' as bc_merchant_shopname");
//        return ResultVM.ok(service.selectPage(page, wrapper));
//    }
}