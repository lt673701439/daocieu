package com.liketry.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@TableName("bank_card")
@Data
@EqualsAndHashCode(callSuper = false)
public class BankCard extends BaseModel<BankCard> {
    private String id;
    private String bcMerchantId;
    private String bcUserName;
    private String bcBankType;
    private String bcBankName;
    private String bcBankNumber;
    private String bcReservedTelephone;
    private String bcIdNumber;
    private String createUserId;
    private Date createTime;
    private String updateUserId;
    private Date updateTime;
    private Integer delflag;
}