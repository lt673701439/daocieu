package com.liketry.interaction.opt.stock.service.impl;
 
import com.liketry.interaction.opt.stock.model.StockBO;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.liketry.interaction.opt.stock.service.IStockService;
import com.taikang.udp.framework.core.service.impl.BaseServiceImpl;
 
  
/**
  * StockServiceImpl
  */
 @Service(IStockService.SERVICE_ID)
 public class StockServiceImpl extends BaseServiceImpl 
 implements IStockService<StockBO>  
  {
  	 	 	
 	/**
	  * 增加数据
	  */
	public void insertObject(StockBO stock) {
		logger.debug("<======StockServiceImpl--insertStock======>");		
		appDao.insert("Stock.addStock",stock);
	} 	
 	
 	/**
      * 修改数据
      */
	public void updateObject(StockBO stock) {
		logger.debug("<======StockServiceImpl--updateStock======>");
		appDao.update("Stock.updateStock",stock);
	}

	 /**
      * 删除数据
      */
	public void deleteObject(StockBO stock) {
		logger.debug("<======StockServiceImpl--deleteStock======>");
		appDao.delete("Stock.deleteStock",stock);
	}
	
	/**
      * 查询数据
      */
	public StockBO findOne(StockBO stock) {
		logger.debug("<======StockServiceImpl--findStock======>");
		
		StockBO stockBackBO=appDao.queryOneObject("Stock.findOneStock", stock);
		return stockBackBO;		
	}
	
	/**
      * 查询所有数据
      */
	public List<StockBO> findAll(StockBO  stock) {
		logger.debug("<======StockServiceImpl--findAllStock======>");
		return appDao.queryForEntityList("Stock.findAllStock", stock);
	}
	
	
	 /**
      * 分页查询数据
      */
	public CurrentPage queryForPage(CurrentPage currentPage) {
		logger.debug("<======StockServiceImpl--queryStockForPage======>");
//		return appDao.queryForPage("Stock.queryStockForPage", currentPage);
		return appDao.queryForPage("Stock.queryStockByConForPage", currentPage);
	}
		
	/**
      * 查询所有数据 ，重新组装为map
      */
	public List<Dto> findAllMap(Dto param){
		logger.debug("<======StockServiceImpl--findAllMapStock======>");		
		return appDao.queryForMapList("Stock.findAllMapStock", param);
	}
	
	/**
     * 查询数据
     */
	public StockBO findStockByCommodityId(StockBO stock) {
		logger.debug("<======StockServiceImpl--findStockByCommodityId======>");
		
		StockBO stockBackBO=appDao.queryOneObject("Stock.findOneStock", stock);
		return stockBackBO;		
	}
 }
  