package com.liketry.interaction.opt.orderdetail.service;

import java.util.Arrays;
import java.util.List;

import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.core.service.IBaseService;

/**
  * IOrderDetailService
  */
 
 public interface IOrderDetailService<T> extends IBaseService<T>{
 
 	final String SERVICE_ID = "orderDetailService";  	
 	
 	/**
 	 * 查询已支付和已播放的订单详情列表
 	 */
 	public List<Dto> findEffectOrderDetailList(Dto param);
 }
 
 
 