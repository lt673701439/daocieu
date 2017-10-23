package com.liketry.domain;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@TableName("bu_make_money")
@Data
@EqualsAndHashCode(callSuper = false)
public class MakeMoney extends BaseModel<MakeMoney>{


	private String id;

	private String name;

	private String produce;

	private String description;

	private String type;

	private String status;

	private String img;

	private String showDate;

	private String createUserId;

	private Date createTime;

	private String updateUserId;

	private Date updateTime;

	private Integer version;

	private String delflag;

}