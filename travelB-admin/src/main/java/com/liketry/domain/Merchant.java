package com.liketry.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

@TableName("merchant")
@Data
@EqualsAndHashCode(callSuper = false)
public class Merchant extends BaseModel<Merchant> {
    private String id;
    private String merchantUserId;
    private String merchantCode;
    private String merchantShopname;
    private String merchantTypeId;
    private String merchantContentId;
    private Integer merchantLevel;
    private Integer merchantCensorStatus;
    private String merchantProvince;
    private String merchantCity;
    private String merchantScenicSpot;
    private String merchantAddress;
    private String merchantIcon;
    private String merchantBusinessLicence;
    private String merchantIdCardUp;
    private String merchantIdCardDown;
    private Integer merchantDivideUpRule;
    private BigDecimal merchantBalance;
    private String merchantQrCode;
    private String merchantContactName;
    private String merchantContactMobile;
    private Boolean merchantOperatingSpring;
    private Boolean merchantOperatingSummer;
    private Boolean merchantOperatingAutumn;
    private Boolean merchantOperatingWinter;
    private Boolean merchantOperatingMon;
    private Boolean merchantOperatingTue;
    private Boolean merchantOperatingWed;
    private Boolean merchantOperatingThu;
    private Boolean merchantOperatingFri;
    private Boolean merchantOperatingSat;
    private Boolean merchantOperatingSun;
    @DateTimeFormat(pattern = "HH:mm")
    @JsonFormat(timezone = "GMT+8", pattern = "HH:mm")
    private Date merchantOperatingStart;//日期
    @DateTimeFormat(pattern = "HH:mm")
    @JsonFormat(timezone = "GMT+8", pattern = "HH:mm")
    private Date merchantOperatingEnd;//日期
    private String merchantDescription;
    private String changeDescription;
    private String createUserId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;//日期
    private String updateUserId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;//日期
    private Integer merchantDelflag;
    @TableField(exist = false)
    private TbDict[] dicts;//非存储字段
    @TableField(exist = false)
    private String merchantProvinceName;//非存储字段
    @TableField(exist = false)
    private String merchantCityName;//非存储字段
    @TableField(exist = false)
    private String merchantTypeName;//非存储字段

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}