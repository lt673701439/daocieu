package com.liketry.interaction.opt.orderdetail.action;

import java.util.Arrays;
import java.util.List;

import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.core.action.IBaseAction;


/**
  * IOrderDetailAction
  */

public interface IOrderDetailAction extends IBaseAction { 

	final String ACTION_ID = "orderDetailAction"; 
	
	/**
	 * 查询已支付和已播放的订单详情列表
	 * @param param
	 * @return
	 */
	public List<Dto> findEffectOrderDetailList(Dto param);
}
