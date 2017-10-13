package com.liketry.interaction.opt.commodity.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.core.action.IBaseAction;


/**
  * ICommodityAction
  */

public interface ICommodityAction extends IBaseAction { 

	final String ACTION_ID = "commodityAction"; 	
	
	public List<Dto> findAllByDateAndTime(Dto param);
	
	public List<Dto> findRepeatByDateAndTime(Dto param);
	
	public Map<String, String> exportImg(Dto param);
	
	public void insertObjectAndUpload(Dto param,HttpServletRequest request);
	
	public void updateObjectAndUpload(Dto param,HttpServletRequest request);
	
}
