package com.liketry.api;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.liketry.domain.BankCard;
import com.liketry.domain.Merchant;
import com.liketry.service.BankCardService;
import com.liketry.service.IdentifyingCodeService;
import com.liketry.service.MerChantService;
import com.liketry.util.CommonUtils;
import com.liketry.util.Constants;
import com.liketry.web.BaseController;
import com.liketry.web.vm.ResultVM;
import com.liketry.web.vm.SmartPageVM;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

/**
 * author Simon
 * create 2017/8/30
 * 银行卡
 */
@RestController
@RequestMapping("api/bank_card_api")
public class BankCardApi extends BaseController<BankCardService, BankCard> {
    private final MerChantService merChantService;
    private final IdentifyingCodeService identifyingCodeService;
    private final int SUCCESS = 0;//通过
    private final int ERROR_EMPTY = 1000;
    private final int ERROR_MERCHANT_ID_EMPTY = 1001;
    private final int ERROR_MERCHANT_ID_FAILED = 1002;
    private final int ERROR_NAME_EMPTY = 1003;
    private final int ERROR_BANK_TYPE_EMPTY = 1004;
    private final int ERROR_BANK_NAME_EMPTY = 1005;
    private final int ERROR_CARD_NUMBER_EMPTY = 1006;
    private final int ERROR_RESERVE_MOBILE_EMPTY = 1007;
    private final int ERROR_INSERT_FAILED = 1008;//插入失败
    private final int ERROR_CARD_EXIST = 1009;//卡号已存在
    private final int ERROR_ID_NUMBER_FAILED = 1010;//身份证错误

    @Autowired
    public BankCardApi(MerChantService merChantService, IdentifyingCodeService identifyingCodeService) {
        this.merChantService = merChantService;
        this.identifyingCodeService = identifyingCodeService;
    }

    @PostMapping("/getSmartData")
    public ResultVM getSmartData(@RequestBody SmartPageVM<BankCard> spage) {
        return ResultVM.error();
    }

    @PostMapping("addBankCard")
    public ResultVM addBankCard(BankCard card, @RequestParam String userMobile, @RequestParam String code) {
        int status = isValidate(card);
        if (status == SUCCESS) {
            EntityWrapper<BankCard> wrapper = new EntityWrapper<BankCard>();
            wrapper.eq("bc_merchant_id", card.getBcMerchantId());
            wrapper.eq("bc_bank_number", card.getBcBankNumber());
            BankCard localCard = service.selectOne(wrapper);
            if (localCard != null) {
                return ResultVM.error(ERROR_CARD_EXIST, "卡号已存在");
            }
            card.setId(CommonUtils.getId());
            card.setDelflag(1);
            String msg = identifyingCodeService.checkIdentifyingCode(userMobile, code, "1");
            if (msg != null) {
                return ResultVM.error(Constants.code_identifying_Error, msg);
            }
            boolean insert = service.insert(card);
            return insert ? ResultVM.ok() : ResultVM.error(ERROR_INSERT_FAILED, "");
        } else {
            return ResultVM.error(status, "");
        }
    }

    @PostMapping("getBankCard")
    public ResultVM getBankCard(@RequestParam String merchantId) {
        EntityWrapper<BankCard> wrapper = new EntityWrapper<BankCard>();
        wrapper.eq("bc_merchant_id", merchantId);
        wrapper.eq("delflag", 1);
        return ResultVM.ok(service.selectList(wrapper));
    }

    private int isValidate(BankCard card) {
        if (card == null)
            return ERROR_EMPTY;
        if (StringUtils.isEmpty(card.getBcMerchantId()))
            return ERROR_MERCHANT_ID_EMPTY;
        Merchant merchant = merChantService.selectById(card.getBcMerchantId());
        if (merchant == null)
            return ERROR_MERCHANT_ID_FAILED;
        if (StringUtils.isEmpty(card.getBcUserName()))
            return ERROR_NAME_EMPTY;
        if (StringUtils.isEmpty(card.getBcBankType()))
            return ERROR_BANK_TYPE_EMPTY;
        if (StringUtils.isEmpty(card.getBcBankName()))
            return ERROR_BANK_NAME_EMPTY;
        if (StringUtils.isEmpty(card.getBcBankNumber()))
            return ERROR_CARD_NUMBER_EMPTY;
        if (StringUtils.isEmpty(card.getBcReservedTelephone()))
            return ERROR_RESERVE_MOBILE_EMPTY;
        if (StringUtils.isBlank(card.getBcIdNumber()) || (card.getBcIdNumber().length() != 15 && card.getBcIdNumber().length() != 18))
            return ERROR_ID_NUMBER_FAILED;
        return SUCCESS;
    }
}
