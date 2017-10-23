package com.liketry.domain;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@TableName("message_template")
@EqualsAndHashCode(callSuper = false)
public class MessageTemplate extends BaseModel<MessageTemplate> {
    private String id;
    private int msgTemplateUsing;
    private int msgTemplateType;
    private String msgTemplateDes;
    private String msgTemplateContent;
    private String create_userId;
    private Date createTime;
    private String updateUserId;
    private Date updateTime;
}