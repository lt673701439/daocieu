package com.liketry.interaction.opt.benisonimg.action;

import javax.servlet.http.HttpServletRequest;

import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.core.action.IBaseAction;


/**
  * IBenisonImgAction
  */

public interface IBenisonImgAction extends IBaseAction { 

	final String ACTION_ID = "benisonImgAction"; 	
	
	public void insertObjectAndUpload(Dto param,HttpServletRequest request);
	
	public void updateObjectAndUpload(Dto param,HttpServletRequest request);
}
