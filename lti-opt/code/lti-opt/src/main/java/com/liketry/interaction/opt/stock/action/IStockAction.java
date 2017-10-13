package com.liketry.interaction.opt.stock.action;

import java.util.Arrays;
import java.util.List;

import com.liketry.interaction.opt.stock.model.StockBO;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.core.action.IBaseAction;


/**
  * IStockAction
  */

public interface IStockAction extends IBaseAction { 

	final String ACTION_ID = "stockAction"; 
	
	public List<StockBO> findAllByOrderId(Dto param);
}
