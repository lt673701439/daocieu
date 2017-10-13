package com.liketry.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@TableName("censor_record")
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class CensorRecord extends BaseModel<CensorRecord> {
    private String id;
    private String crUserId;
    private String crMerchantId;
    private String crDescription;
    private String createUserId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @TableField(exist = false)
    private String merchantShopname;//商户名
    @TableField(exist = false)
    private String username;//系统创建名

    public CensorRecord(String id, String crUserId, String crMerchantId, String crDescription, String createUserId) {
        this.id = id;
        this.crUserId = crUserId;
        this.crMerchantId = crMerchantId;
        this.crDescription = crDescription;
        this.createUserId = createUserId;
    }
}