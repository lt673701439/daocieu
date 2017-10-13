package com.liketry.domain;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * author Simon
 * create 2017/8/31
 * 序列号
 */
@Data
@TableName("sys_sequence")
@EqualsAndHashCode(callSuper = false)
public class SysSequence extends BaseModel<SysSequence> {
    private String id;
    private Long sysValue;
    private String createUserId;
    private Date createTime;
    private String updateUserId;
    private Date updateTime;

    public SysSequence() {
    }

    public SysSequence(String id, Long sysValue) {
        this.id = id;
        this.sysValue = sysValue;
    }
}
