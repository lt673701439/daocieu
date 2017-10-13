package com.liketry.interaction.opt.commoditycost.service.impl;
 
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.liketry.interaction.opt.commoditycost.service.ICommodityCostService;
import com.taikang.udp.framework.core.service.impl.BaseServiceImpl;
import com.liketry.interaction.opt.commoditycost.model.CommodityCostBO;
 
  
/**
  * CommodityCostServiceImpl
  */
 @Service(ICommodityCostService.SERVICE_ID)
 public class CommodityCostServiceImpl extends BaseServiceImpl 
 implements ICommodityCostService<CommodityCostBO>  
  {
  	 	 	
 	/**
	  * 增加数据
	  */
	public void insertObject(CommodityCostBO commodityCost) {
		logger.debug("<======CommodityCostServiceImpl--insertCommodityCost======>");		
		appDao.insert("CommodityCost.addCommodityCost",commodityCost);
	} 	
 	
 	/**
      * 修改数据
      */
	public void updateObject(CommodityCostBO commodityCost) {
		logger.debug("<======CommodityCostServiceImpl--updateCommodityCost======>");
		appDao.update("CommodityCost.updateCommodityCost",commodityCost);
	}

	 /**
      * 删除数据
      */
	public void deleteObject(CommodityCostBO commodityCost) {
		logger.debug("<======CommodityCostServiceImpl--deleteCommodityCost======>");
		appDao.delete("CommodityCost.deleteCommodityCost",commodityCost);
	}
	
	/**
      * 查询数据
      */
	public CommodityCostBO findOne(CommodityCostBO commodityCost) {
		logger.debug("<======CommodityCostServiceImpl--findCommodityCost======>");
		
		CommodityCostBO commodityCostBackBO=appDao.queryOneObject("CommodityCost.findOneCommodityCost", commodityCost);
		return commodityCostBackBO;		
	}
	
	/**
      * 查询所有数据
      */
	public List<CommodityCostBO> findAll(CommodityCostBO  commodityCost) {
		logger.debug("<======CommodityCostServiceImpl--findAllCommodityCost======>");
		return appDao.queryForEntityList("CommodityCost.findAllCommodityCost", commodityCost);
	}
	
	
	 /**
      * 分页查询数据
      */
	public CurrentPage queryForPage(CurrentPage currentPage) {
		logger.debug("<======CommodityCostServiceImpl--queryCommodityCostByConForPage======>");
//		return appDao.queryForPage("CommodityCost.queryCommodityCostForPage", currentPage);
		return appDao.queryForPage("CommodityCost.queryCommodityCostByConForPage", currentPage);
	}
		
	/**
      * 查询所有数据 ，重新组装为map
      */
	public List<Dto> findAllMap(Dto param){
		logger.debug("<======CommodityCostServiceImpl--findAllMapCommodityCost======>");		
		return appDao.queryForMapList("CommodityCost.findAllMapCommodityCost", param);
	}
 }
  