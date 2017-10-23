package com.liketry.web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.liketry.domain.Censor;
import com.liketry.domain.CensorRecord;
import com.liketry.domain.Merchant;
import com.liketry.domain.TbDict;
import com.liketry.service.CensorRecordService;
import com.liketry.service.CensorService;
import com.liketry.service.MerChantService;
import com.liketry.service.TbDictService;
import com.liketry.util.*;
import com.liketry.web.vm.CensorBodyVM;
import com.liketry.web.vm.ResultVM;
import com.liketry.web.vm.SmartPageVM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

/**
 * author Simon
 * create 2017/8/28
 * 商户
 */
@Api(value = "商户信息", description = "供平台管理调用")
@RestController
@RequestMapping("merchant")
public class MerchantController extends BaseController<MerChantService, Merchant> {
    private final int ERROR_DELETE_FAILED = 1001;//更新失败
    private final CensorService censorService;
    private final CensorRecordService censorRecordService;
    private final TbDictService tbDictService;

    @Autowired
    public MerchantController(CensorService censorService, CensorRecordService censorRecordService, TbDictService tbDictService) {
        this.censorService = censorService;
        this.censorRecordService = censorRecordService;
        this.tbDictService = tbDictService;
    }

    @Override
    @PostMapping("/getSmartData")
    public ResultVM getSmartData(@RequestBody SmartPageVM<Merchant> spage) {
        Merchant merchant = spage.getSearch();
        merchant.setMerchantDelflag(1);
        spage.setSearch(merchant);
        ResultVM smartData = super.getSmartData(spage);
        Page<Merchant> resultPage = (Page<Merchant>) smartData.getResult();
        List<Merchant> datas = resultPage.getRecords();
        if (datas != null && !datas.isEmpty()) {
            for (int i = 0; i < datas.size(); i++) {
                String itemId = datas.get(i).getMerchantTypeId();
                TbDict tb = tbDictService.selectById(itemId);
                if (tb != null)
                    datas.get(i).setMerchantTypeName(tb.getText());
            }
            resultPage.setRecords(datas);
        }
        smartData.setResult(resultPage);
        return smartData;
    }

    @PostMapping("/getSpecialSmartData")
    public ResultVM getSpecialSmartData(@RequestBody SmartPageVM<Merchant> spage) {
        Merchant merchant = spage.getSearch();
        merchant.setMerchantDelflag(1);
        spage.setSearch(merchant);
        Page<Merchant> page = new Page<Merchant>(spage.getPagination().getStart(), spage.getPagination().getNumber());
        if (StringUtils.isBlank(spage.getSort().getPredicate())) {
            spage.getSort().setPredicate("update_time");
        }
        page.setOrderByField(spage.getSort().getPredicate());
        page.setAsc(spage.getSort().getReverse());
        EntityWrapper<Merchant> wrapper = new EntityWrapper<Merchant>();
        if (spage.getSearch() != null) {
            Field[] fields = spage.getSearch().getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                try {
                    fields[i].setAccessible(true);
                    Object value = fields[i].get(spage.getSearch());
                    if (null != value && !value.equals("")) {
                        String fieldname = StringTools.underscoreName(fields[i].getName());
                        wrapper.like(fieldname, value.toString());
                    }
                    fields[i].setAccessible(false);
                } catch (Exception e) {
                }
            }
        }
        wrapper.notIn("merchant_censor_status", 2, 3);
        Page<Merchant> resultPage = service.selectPage(page, wrapper);
        List<Merchant> datas = resultPage.getRecords();
        if (datas != null && !datas.isEmpty()) {
            for (int i = 0; i < datas.size(); i++) {
                String itemId = datas.get(i).getMerchantTypeId();
                TbDict tb = tbDictService.selectById(itemId);
                if (tb != null)
                    datas.get(i).setMerchantTypeName(tb.getText());
            }
            resultPage.setRecords(datas);
        }
        return ResultVM.ok(resultPage);
    }

    @Override
    @PutMapping
    public ResultVM update(@RequestBody Merchant merchant) {
        merchant.setUpdateTime(new Date());
        merchant.setUpdateUserId(ShiroUtils.getUserId());
        censorRecordService.insert(new CensorRecord(CommonUtils.getId(), merchant.getId(), ShiroUtils.getUserId(), merchant.getChangeDescription(), ShiroUtils.getUserId()));
        if (service.updateById(merchant)) {
            return ResultVM.ok();
        } else {
            return ResultVM.error();
        }
    }

    @RequestMapping("delete")
    public ResultVM deleteMerchant(@RequestParam String id) {
        Merchant merchant = new Merchant();
        merchant.setId(id);
        merchant.setMerchantDelflag(0);
        boolean update = service.updateById(merchant);
        return update ? ResultVM.ok() : ResultVM.error(String.valueOf(ERROR_DELETE_FAILED));
    }

    @ApiOperation(value = "审核")
    @PostMapping("censor")
    public ResultVM censorMerchant(@RequestBody CensorBodyVM mv) {
        if (mv == null || StringUtils.isBlank(mv.getMerchantId()) || mv.getStatus() == null)
            return ResultVM.error();
        if (!mv.getStatus() && StringUtils.isBlank(mv.getReason()))
            return ResultVM.error();
        boolean status = mv.getStatus();
        Censor censor = new Censor(mv.getMerchantId(), status, mv.getReason());
        censor.setCreateUserId(ShiroUtils.getUserId());
        boolean insertCensor = censorService.insert(censor);
        Merchant merchant = service.selectById(mv.getMerchantId());
        merchant.setMerchantCensorStatus(status ? Constants.CENSOR_SUCCESS : Constants.CENSOR_FAILED);
        if (status) {
            String codeUri = QRCodeUtils.getBenisonQRCode(merchant.getId());
            merchant.setMerchantQrCode(codeUri);
        }
        boolean insertMerchant = service.updateById(merchant);
        censorRecordService.insert(new CensorRecord(CommonUtils.getId(), merchant.getMerchantUserId(), merchant.getId(), "系统审核", ShiroUtils.getUserId()));
        return insertCensor && insertMerchant ? ResultVM.ok() : ResultVM.error(String.valueOf(Constants.ERROR_INSERT_FAILED));
    }
}