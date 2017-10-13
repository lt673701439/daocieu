package com.liketry.interaction.opt.stock.service;

import java.util.Arrays;
import java.util.List;

import com.liketry.interaction.opt.stock.model.StockBO;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.core.service.IBaseService;

/**
  * IStockService
  */
 
 public interface IStockService<T> extends IBaseService<T>{
 
 	final String SERVICE_ID = "stockService";  	
 	
 	public StockBO findStockByCommodityId(StockBO stock);
 }
 
 
 