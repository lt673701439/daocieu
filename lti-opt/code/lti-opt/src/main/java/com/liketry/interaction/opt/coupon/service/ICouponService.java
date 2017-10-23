package com.liketry.interaction.opt.coupon.service;

import java.util.Arrays;
import java.util.List;

import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.core.service.IBaseService;

/**
  * ICouponService
  */
 
 public interface ICouponService<T> extends IBaseService<T>{
 
 	final String SERVICE_ID = "couponService";

	List<Dto> findAllCouponMap(Dto param);  	
 }
 
 
 