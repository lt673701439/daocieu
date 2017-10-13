package com.liketry.service.impl;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.liketry.domain.Merchant;
import com.liketry.domain.OrderRecord;
import com.liketry.domain.RecDis;
import com.liketry.service.CodeService;
import com.liketry.service.MerChantService;
import com.liketry.service.OrderRecordService;
import com.liketry.service.RecDisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * author Simon
 * create 2017/8/30
 * 编码服务
 */
@Service
public class CodeServiceImpl implements CodeService {
    private final String PREFIX_RCP = "RCP";
    private final String PREFIX_PYM = "PYM";
    private final RecDisService recDisService;
    private final MerChantService merChantService;
    private final OrderRecordService orderRecordService;

    @Autowired
    public CodeServiceImpl(MerChantService merChantService, OrderRecordService orderRecordService, RecDisService recDisService) {
        this.merChantService = merChantService;
        this.orderRecordService = orderRecordService;
        this.recDisService = recDisService;
    }

    /**
     * author Simon
     * create 2017/8/30
     * 获取商户编码
     */
    @Override
    public String getCTMCode() {
        EntityWrapper<Merchant> wrapper = new EntityWrapper<Merchant>();
        wrapper.orderBy("create_time", false);
        Merchant merchant = merChantService.selectOne(wrapper);
        if (merchant == null) {
            return "CTM00001";
        } else {
            final int code = Integer.valueOf(merchant.getMerchantCode().substring(3)) + 1;
            return new DecimalFormat("CTM00000").format(code);
        }
    }

    /**
     * author Simon
     * create 2017/8/30
     * 获取订账单编码
     */
    @Override
    public String getBODCode(String merchantCode) {
        Date current = new Date();
        EntityWrapper<OrderRecord> wrapper = new EntityWrapper<OrderRecord>();
        wrapper.where("to_days(create_time) = to_days({0})", current).orderBy("create_time", false);
        OrderRecord orderRecord = orderRecordService.selectOne(wrapper);
        final String prefix = "BOD" + merchantCode + new SimpleDateFormat("yyyyMMdd").format(current);
        if (orderRecord == null) {
            return prefix + "00001";
        } else {
            String num = orderRecord.getOrCode();
            int code = Integer.valueOf(num.substring(num.length() - 3)) + 1;
            return prefix + new DecimalFormat("00000").format(code);
        }
    }

    /**
     * 获取首付款单编码
     *
     * @param merchantCode 商户编号
     * @param isRCP        是否是收款
     * @return 编码
     */
    @Override
    public String getBillCode(String merchantCode, boolean isRCP) {
        final String prefix = (isRCP ? PREFIX_RCP : PREFIX_PYM) + merchantCode + new SimpleDateFormat("yyyyMMdd").format(new Date());
        EntityWrapper<RecDis> wrapper = new EntityWrapper<RecDis>();
        wrapper.like("rec_ids_code", prefix, SqlLike.RIGHT).orderBy("create_time", false);
        RecDis orderRecord = recDisService.selectOne(wrapper);
        if (orderRecord == null) {
            return prefix + "00001";
        } else {
            String num = orderRecord.getRecIdsCode();
            int code = Integer.valueOf(num.substring(num.length() - 5)) + 1;
            return prefix + new DecimalFormat("00000").format(code);
        }
    }
}