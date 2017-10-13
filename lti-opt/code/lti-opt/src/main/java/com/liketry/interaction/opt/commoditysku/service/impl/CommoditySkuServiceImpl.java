package com.liketry.interaction.opt.commoditysku.service.impl;
 
import com.liketry.interaction.opt.commoditysku.model.CommoditySkuBO;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.core.service.impl.BaseServiceImpl;
import com.liketry.interaction.opt.commoditysku.service.ICommoditySkuService;
 
  
/**
  * CommoditySkuServiceImpl
  */
 @Service(ICommoditySkuService.SERVICE_ID)
 public class CommoditySkuServiceImpl extends BaseServiceImpl 
 implements ICommoditySkuService<CommoditySkuBO>  
  {
  	 	 	
 	/**
	  * 增加数据
	  */
	public void insertObject(CommoditySkuBO commoditySku) {
		logger.debug("<======CommoditySkuServiceImpl--insertCommoditySku======>");		
		appDao.insert("CommoditySku.addCommoditySku",commoditySku);
	} 	
 	
 	/**
      * 修改数据
      */
	public void updateObject(CommoditySkuBO commoditySku) {
		logger.debug("<======CommoditySkuServiceImpl--updateCommoditySku======>");
		appDao.update("CommoditySku.updateCommoditySku",commoditySku);
	}

	 /**
      * 删除数据
      */
	public void deleteObject(CommoditySkuBO commoditySku) {
		logger.debug("<======CommoditySkuServiceImpl--deleteCommoditySku======>");
		appDao.delete("CommoditySku.deleteCommoditySku",commoditySku);
	}
	
	/**
      * 查询数据
      */
	public CommoditySkuBO findOne(CommoditySkuBO commoditySku) {
		logger.debug("<======CommoditySkuServiceImpl--findCommoditySku======>");
		
		CommoditySkuBO commoditySkuBackBO=appDao.queryOneObject("CommoditySku.findOneCommoditySku", commoditySku);
		return commoditySkuBackBO;		
	}
	
	/**
      * 查询所有数据
      */
	public List<CommoditySkuBO> findAll(CommoditySkuBO  commoditySku) {
		logger.debug("<======CommoditySkuServiceImpl--findAllCommoditySku======>");
		return appDao.queryForEntityList("CommoditySku.findAllCommoditySku", commoditySku);
	}
	
	
	 /**
      * 分页查询数据
      */
	public CurrentPage queryForPage(CurrentPage currentPage) {
		logger.debug("<======CommoditySkuServiceImpl--queryCommoditySkuForPage======>");
//		return appDao.queryForPage("CommoditySku.queryCommoditySkuForPage", currentPage);
		return appDao.queryForPage("CommoditySku.queryCommoditySkuByConForPage", currentPage);
	}
		
	/**
      * 查询所有数据 ，重新组装为map
      */
	public List<Dto> findAllMap(Dto param){
		logger.debug("<======CommoditySkuServiceImpl--findAllMapCommoditySku======>");		
		return appDao.queryForMapList("CommoditySku.findAllMapCommoditySku", param);
	}
	
	/**
     * 查询最新的商品sku
     */
	public CommoditySkuBO findLastOne(CommoditySkuBO commoditySku) {
		logger.debug("<======CommoditySkuServiceImpl--findLastOne======>");
		
		CommoditySkuBO commoditySkuBackBO=appDao.queryOneObject("CommoditySku.findLastOneCommoditySku", commoditySku);
		return commoditySkuBackBO;		
	}
 }
  