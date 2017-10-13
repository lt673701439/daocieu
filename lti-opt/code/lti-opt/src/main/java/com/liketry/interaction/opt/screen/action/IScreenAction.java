package com.liketry.interaction.opt.screen.action;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.core.action.IBaseAction;


/**
  * IScreenAction
  */

public interface IScreenAction extends IBaseAction { 

	final String ACTION_ID = "screenAction"; 	
	
	public List<Dto> findAllBySpotId(Dto param);
	
	public void insertObjectAndUpload(Dto param,HttpServletRequest request);
	
	public void updateObjectAndUpload(Dto param,HttpServletRequest request);
}
