package com.liketry.interaction.opt.promotiondetail.action;

import java.util.Arrays;
import java.util.List;

import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.core.action.IBaseAction;


/**
  * IPromotionDetailAction
  */

public interface IPromotionDetailAction extends IBaseAction { 

	final String ACTION_ID = "promotionDetailAction"; 	
	
	/**
     * 查询商品列表
     */
	public List<Dto> findAllCommodityById(Dto param);
}
