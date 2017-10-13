package com.liketry.interaction.opt.stockdetail.service.impl;
 
import java.util.Arrays;
import java.util.List;
import com.liketry.interaction.opt.stockdetail.service.IStockDetailService;
import org.springframework.stereotype.Service;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.liketry.interaction.opt.stockdetail.model.StockDetailBO;
import com.taikang.udp.framework.core.service.impl.BaseServiceImpl;
 
  
/**
  * StockDetailServiceImpl
  */
 @Service(IStockDetailService.SERVICE_ID)
 public class StockDetailServiceImpl extends BaseServiceImpl 
 implements IStockDetailService<StockDetailBO>  
  {
  	 	 	
 	/**
	  * 增加数据
	  */
	public void insertObject(StockDetailBO stockDetail) {
		logger.debug("<======StockDetailServiceImpl--insertStockDetail======>");		
		appDao.insert("StockDetail.addStockDetail",stockDetail);
	} 	
 	
 	/**
      * 修改数据
      */
	public void updateObject(StockDetailBO stockDetail) {
		logger.debug("<======StockDetailServiceImpl--updateStockDetail======>");
		appDao.update("StockDetail.updateStockDetail",stockDetail);
	}

	 /**
      * 删除数据
      */
	public void deleteObject(StockDetailBO stockDetail) {
		logger.debug("<======StockDetailServiceImpl--deleteStockDetail======>");
		appDao.delete("StockDetail.deleteStockDetail",stockDetail);
	}
	
	/**
      * 查询数据
      */
	public StockDetailBO findOne(StockDetailBO stockDetail) {
		logger.debug("<======StockDetailServiceImpl--findStockDetail======>");
		
		StockDetailBO stockDetailBackBO=appDao.queryOneObject("StockDetail.findOneStockDetail", stockDetail);
		return stockDetailBackBO;		
	}
	
	/**
      * 查询所有数据
      */
	public List<StockDetailBO> findAll(StockDetailBO  stockDetail) {
		logger.debug("<======StockDetailServiceImpl--findAllStockDetail======>");
		return appDao.queryForEntityList("StockDetail.findAllStockDetail", stockDetail);
	}
	
	
	 /**
      * 分页查询数据
      */
	public CurrentPage queryForPage(CurrentPage currentPage) {
		logger.debug("<======StockDetailServiceImpl--queryStockDetailForPage======>");
		return appDao.queryForPage("StockDetail.queryStockDetailForPage", currentPage);
	}
		
	/**
      * 查询所有数据 ，重新组装为map
      */
	public List<Dto> findAllMap(Dto param){
		logger.debug("<======StockDetailServiceImpl--findAllMapStockDetail======>");		
		return appDao.queryForMapList("StockDetail.findAllMapStockDetail", param);
	}
 }
  