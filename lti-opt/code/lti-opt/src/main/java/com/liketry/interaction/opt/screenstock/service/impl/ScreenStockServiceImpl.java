package com.liketry.interaction.opt.screenstock.service.impl;
 
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.core.service.impl.BaseServiceImpl;
import com.liketry.interaction.opt.screenstock.model.ScreenStockBO;
import com.liketry.interaction.opt.screenstock.service.IScreenStockService;
 
  
/**
  * ScreenStockServiceImpl
  */
 @Service(IScreenStockService.SERVICE_ID)
 public class ScreenStockServiceImpl extends BaseServiceImpl 
 implements IScreenStockService<ScreenStockBO>  
  {
  	 	 	
 	/**
	  * 增加数据
	  */
	public void insertObject(ScreenStockBO screenStock) {
		logger.debug("<======ScreenStockServiceImpl--insertScreenStock======>");		
		appDao.insert("ScreenStock.addScreenStock",screenStock);
	} 	
 	
 	/**
      * 修改数据
      */
	public void updateObject(ScreenStockBO screenStock) {
		logger.debug("<======ScreenStockServiceImpl--updateScreenStock======>");
		appDao.update("ScreenStock.updateScreenStock",screenStock);
	}

	 /**
      * 删除数据
      */
	public void deleteObject(ScreenStockBO screenStock) {
		logger.debug("<======ScreenStockServiceImpl--deleteScreenStock======>");
		appDao.delete("ScreenStock.deleteScreenStock",screenStock);
	}
	
	/**
      * 查询单挑数据，原方法已经改变加入了关联屏幕信息
      */
	public ScreenStockBO findOne(ScreenStockBO screenStock) {
		logger.debug("<======ScreenStockServiceImpl--findScreenStock======>");
		ScreenStockBO screenStockBackBO=appDao.queryOneObject("ScreenStock.findNewOneScreenStock", screenStock);
//		ScreenStockBO screenStockBackBO=appDao.queryOneObject("ScreenStock.findOneScreenStock", screenStock);
		return screenStockBackBO;		
	}
	
	/**
      * 查询所有数据
      */
	public List<ScreenStockBO> findAll(ScreenStockBO  screenStock) {
		logger.debug("<======ScreenStockServiceImpl--findAllScreenStock======>");
		return appDao.queryForEntityList("ScreenStock.findAllScreenStock", screenStock);
	}
	
	
	 /**
      * 分页查询数据
      */
	public CurrentPage queryForPage(CurrentPage currentPage) {
		logger.debug("<======ScreenStockServiceImpl--queryScreenStockForPage======>");
		return appDao.queryForPage("ScreenStock.queryScreenStockForPageByCon", currentPage);
//		return appDao.queryForPage("ScreenStock.queryScreenStockForPage", currentPage);
	}
		
	/**
      * 查询所有数据 ，重新组装为map
      */
	public List<Dto> findAllMap(Dto param){
		logger.debug("<======ScreenStockServiceImpl--findAllMapScreenStock======>");		
		return appDao.queryForMapList("ScreenStock.findAllMapScreenStock", param);
	}
	
	/**
     * 查询是否存在有时间冲突的屏幕
     */
	public List<Dto> findRepeatByDateAndTime(Dto param){
		logger.debug("<======ScreenStockServiceImpl--findRepeatByDateAndTime======>");		
		return appDao.queryForMapList("ScreenStock.findRepeatByDateAndTime", param);
	}
 }
  