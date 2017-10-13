package com.liketry.interaction.opt.commodity.service.impl;
 
import com.liketry.interaction.opt.commodity.model.CommodityBO;
import java.util.Arrays;
import java.util.List;
import com.liketry.interaction.opt.commodity.service.ICommodityService;
import org.springframework.stereotype.Service;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.core.service.impl.BaseServiceImpl;
 
  
/**
  * CommodityServiceImpl
  */
 @Service(ICommodityService.SERVICE_ID)
 public class CommodityServiceImpl extends BaseServiceImpl 
 implements ICommodityService<CommodityBO>  
  {
  	 	 	
 	/**
	  * 增加数据
	  */
	public void insertObject(CommodityBO commodity) {
		logger.debug("<======CommodityServiceImpl--insertCommodity======>");		
		appDao.insert("Commodity.addCommodity",commodity);
	} 	
 	
 	/**
      * 修改数据
      */
	public void updateObject(CommodityBO commodity) {
		logger.debug("<======CommodityServiceImpl--updateCommodity======>");
		appDao.update("Commodity.updateCommodity",commodity);
	}

	 /**
      * 删除数据
      */
	public void deleteObject(CommodityBO commodity) {
		logger.debug("<======CommodityServiceImpl--deleteCommodity======>");
		appDao.delete("Commodity.deleteCommodity",commodity);
	}
	
	/**
      * 查询数据
      */
	public CommodityBO findOne(CommodityBO commodity) {
		logger.debug("<======CommodityServiceImpl--findCommodity======>");
		
		CommodityBO commodityBackBO=appDao.queryOneObject("Commodity.findOneCommodity", commodity);
		return commodityBackBO;		
	}
	
	/**
      * 查询所有数据
      */
	public List<CommodityBO> findAll(CommodityBO  commodity) {
		logger.debug("<======CommodityServiceImpl--findAllCommodity======>");
		return appDao.queryForEntityList("Commodity.findAllCommodity", commodity);
	}
	
	
	 /**
      * 分页查询数据
      */
	public CurrentPage queryForPage(CurrentPage currentPage) {
		logger.debug("<======CommodityServiceImpl--queryCommodityForPage======>");
//		return appDao.queryForPage("Commodity.queryCommodityForPage", currentPage);
		return appDao.queryForPage("Commodity.queryCommodityByConForPage", currentPage);
	}
		
	/**
      * 查询所有数据 ，重新组装为map
      */
	public List<Dto> findAllMap(Dto param){
		logger.debug("<======CommodityServiceImpl--findAllMapCommodity======>");		
		return appDao.queryForMapList("Commodity.findAllMapCommodity", param);
	}
	
	/**
     * 根据日期和时间查询商品列表
     */
	public List<Dto> findAllByDateAndTime(Dto param){
		logger.debug("<======CommodityServiceImpl--findAllByDateAndTime======>");		
		return appDao.queryForMapList("Commodity.findAllMapByDateAndTime", param);
	}
	
	/**
     * 根据日期和时间查询重叠商品列表
     */
	public List<Dto> findRepeatByDateAndTime(Dto param){
		logger.debug("<======CommodityServiceImpl--findRepeatByDateAndTime======>");		
		return appDao.queryForMapList("Commodity.findRepeatByDateAndTime", param);
	}
 }
  