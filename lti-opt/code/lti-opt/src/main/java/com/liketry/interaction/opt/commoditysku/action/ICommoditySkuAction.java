package com.liketry.interaction.opt.commoditysku.action;

import java.util.Arrays;

import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.core.action.IBaseAction;


/**
  * ICommoditySkuAction
  */

public interface ICommoditySkuAction extends IBaseAction { 

	final String ACTION_ID = "commoditySkuAction"; 	
	
	/**
     * 查询最新的商品sku
     */
	public Dto findLastOne(Dto param);
}
