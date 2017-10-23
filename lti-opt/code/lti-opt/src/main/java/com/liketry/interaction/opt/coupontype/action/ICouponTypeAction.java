package com.liketry.interaction.opt.coupontype.action;

import java.util.Arrays;

import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.core.action.IBaseAction;


/**
  * ICouponTypeAction
  */

public interface ICouponTypeAction extends IBaseAction { 

	final String ACTION_ID = "couponTypeAction"; 	
	
	public Dto findLastOne(Dto param);
}
