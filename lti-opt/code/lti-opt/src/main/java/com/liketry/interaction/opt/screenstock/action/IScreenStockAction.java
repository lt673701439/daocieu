package com.liketry.interaction.opt.screenstock.action;

import java.util.Arrays;
import java.util.List;

import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.core.action.IBaseAction;


/**
  * IScreenStockAction
  */

public interface IScreenStockAction extends IBaseAction { 

	final String ACTION_ID = "screenStockAction"; 	
	
	public List<Dto> findRepeatByDateAndTime(Dto param);
}
