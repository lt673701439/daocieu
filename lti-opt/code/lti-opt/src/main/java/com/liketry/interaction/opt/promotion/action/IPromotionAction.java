package com.liketry.interaction.opt.promotion.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.core.action.IBaseAction;


/**
  * IPromotionAction
  */

public interface IPromotionAction extends IBaseAction { 

	final String ACTION_ID = "promotionAction"; 	
	public void insertObjectAndUpload(Dto param,HttpServletRequest request);
	public void updateObjectAndUpload(Dto param,HttpServletRequest request,String description);
	Map<String,Object> getDesc(Dto param);
}
