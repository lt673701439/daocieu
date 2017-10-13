package com.liketry.interaction.opt.promotiondetail.service;

import java.util.Arrays;
import java.util.List;

import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.core.service.IBaseService;

/**
  * IPromotionDetailService
  */
 
 public interface IPromotionDetailService<T> extends IBaseService<T>{
 
 	final String SERVICE_ID = "promotionDetailService";  
 	
 	/**
     *  查询商品列表
     */
	public List<Dto> findAllCommodityById(Dto param);
 }
 
 
 