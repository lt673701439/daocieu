package com.liketry.interaction.opt.benison.action;

import java.util.Arrays;
import java.util.List;

import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.core.action.IBaseAction;


/**
  * IBenisonAction
  */

public interface IBenisonAction extends IBaseAction { 

	final String ACTION_ID = "benisonAction"; 	
	
	public List<Dto> findAllBySpotId(Dto param);
}
