package com.liketry.interaction.opt.coupontype.service;

import java.util.Arrays;
import java.util.List;

import com.liketry.interaction.opt.coupontype.model.CouponTypeBO;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.core.service.IBaseService;

/**
  * ICouponTypeService
  */
 
 public interface ICouponTypeService<T> extends IBaseService<T>{
 
 	final String SERVICE_ID = "couponTypeService";  	
 	
 	public CouponTypeBO findLastOne(CouponTypeBO couponType) ;
 	
 }
 
 
 