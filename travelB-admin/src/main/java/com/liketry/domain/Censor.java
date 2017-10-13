package com.liketry.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableName;

import com.liketry.util.CommonUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

@TableName("censor")
@Data
@EqualsAndHashCode(callSuper = false)
public class Censor extends BaseModel<Censor> {

    private String id;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date censorCreatetime;
    private String censorUserId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date censorTime;
    private Boolean censorStatus;
    private String censorFailureReason;
    private String createUserId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private String updateUserId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    public Censor() {
    }

    //新建审核使用
    public Censor(String censorUserId, Boolean censorStatus, String censorFailureReason) {
        Date date = new Date();
        this.id = CommonUtils.getId();
        this.censorTime = date;
        this.censorCreatetime = date;
        this.censorUserId = censorUserId;
        this.censorStatus = censorStatus;
        this.censorFailureReason = censorFailureReason;
    }
}