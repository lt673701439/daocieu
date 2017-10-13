package com.liketry.interaction.opt.screenstock.service;

import java.util.Arrays;
import java.util.List;

import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.core.service.IBaseService;

/**
  * IScreenStockService
  */
 
 public interface IScreenStockService<T> extends IBaseService<T>{
 
 	final String SERVICE_ID = "screenStockService";  
 	
 	public List<Dto> findRepeatByDateAndTime(Dto param);
 }
 
 
 