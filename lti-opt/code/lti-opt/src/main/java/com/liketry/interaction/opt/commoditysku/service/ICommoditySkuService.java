package com.liketry.interaction.opt.commoditysku.service;

import java.util.Arrays;

import com.liketry.interaction.opt.commoditysku.model.CommoditySkuBO;
import com.taikang.udp.framework.core.service.IBaseService;

/**
  * ICommoditySkuService
  */
 
 public interface ICommoditySkuService<T> extends IBaseService<T>{
 
 	final String SERVICE_ID = "commoditySkuService";  
 	
 	/**
     * 查询最新的商品sku
     */
	public CommoditySkuBO findLastOne(CommoditySkuBO commoditySku);
 }
 
 
 