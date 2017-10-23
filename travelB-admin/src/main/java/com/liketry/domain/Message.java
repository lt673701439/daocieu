package com.liketry.domain;

import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * author Simon
 * create 2017/9/4
 * 消息
 */
@Data
@TableName("message")
@EqualsAndHashCode(callSuper = false)
public class Message extends BaseModel<Message> {
    private String id;
    private Integer messageType;
    private String messageTitle;
    private String messageContent;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date messagePublishTime;
    private String messageMerchantId;
    private String messageImg;
    private String messagePromotionId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date messageCancelTime;
    private String createUserId;
    private Date createTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String updateUserId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    private Integer delflag;
    private Boolean messageImmediatelyPush;
    private Boolean messageReceiveAll;
    private Boolean messageIsPush;
    private Boolean messageIsSms;
}