package com.liketry.interaction.opt.benisontemplate.action;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.core.action.IBaseAction;


/**
  * IBenisonTemplateAction
  */

public interface IBenisonTemplateAction extends IBaseAction { 

	final String ACTION_ID = "benisonTemplateAction"; 	
	
	public List<Dto> findAllByBenisonId(Dto param);
	public List<Dto> findAllByImgId(Dto param);
	public List<Dto> findAllByTypeId(Dto param);
	public List<Dto> findAllByScreenId(Dto param);
	public Dto findLastOne(Dto param);
	
	public void insertObjectAndUpload(Dto param,HttpServletRequest request); 
	
	public void updateObjectAndUpload(Dto param,HttpServletRequest request);
}
