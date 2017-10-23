package com.liketry.interaction.opt.coupon.action;

import java.util.Arrays;
import java.util.List;

import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.core.action.IBaseAction;


/**
  * ICouponAction
  */

public interface ICouponAction extends IBaseAction { 

	final String ACTION_ID = "couponAction"; 	
	
	public String makeActivatedcode(Dto param);
	
	public List<Dto> findAllCoupon(Dto param);
}
