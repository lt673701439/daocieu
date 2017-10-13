package com.liketry.interaction.opt.commodity.service;

import java.util.Arrays;
import java.util.List;

import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.core.service.IBaseService;

/**
  * ICommodityService
  */
 
 public interface ICommodityService<T> extends IBaseService<T>{
 
 	final String SERVICE_ID = "commodityService";  	
 	
 	public List<Dto> findAllByDateAndTime(Dto param);
 	
 	public List<Dto> findRepeatByDateAndTime(Dto param);
 }
 
 
 