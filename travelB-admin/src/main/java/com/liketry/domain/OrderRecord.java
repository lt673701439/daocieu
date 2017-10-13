package com.liketry.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

@TableName("order_record")
@Data
@EqualsAndHashCode(callSuper = false)
public class OrderRecord extends BaseModel<OrderRecord> {
    private String id;
    private String orCode;
    private String orSourceId;
    private String orSourceCode;
    private String orMerchantId;
    private Integer orStatus;
    private Integer orType;
    private String orUserId;
    private String orUserName;
    private String orUserIcon;
    private String orMobile;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orOrderTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orPayTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orBackTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orRefundTime;
    private BigDecimal orPayPrice;
    private BigDecimal orMerchantGain;
    private BigDecimal orRefundPrice;
    private BigDecimal orMerchantBackPrice;
    private BigDecimal orBalance;
    private String transactionNo;
    private String orInfo;
    private String createUserId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private String updateUserId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    @TableField(exist = false)
    private String bcMerchantShopname;
}