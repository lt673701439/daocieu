package com.liketry.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableName;

import com.liketry.util.CommonUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

@TableName("identifying_code")
@Data
@EqualsAndHashCode(callSuper = false)
public class IdentifyingCode extends BaseModel<IdentifyingCode> {

    private String id;
    private String mobile;
    private String code;
    private String type;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sendTime;
    private String createUserId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private String updateUserId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}