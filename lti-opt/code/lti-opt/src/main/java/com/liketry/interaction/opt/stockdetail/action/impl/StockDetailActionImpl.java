package com.liketry.interaction.opt.stockdetail.action.impl;


import java.util.Arrays;
import java.util.List;
import com.liketry.interaction.opt.stockdetail.service.IStockDetailService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import com.liketry.interaction.opt.stockdetail.model.StockDetailBO;
import com.liketry.interaction.opt.stockdetail.action.IStockDetailAction;
import com.taikang.udp.framework.core.action.impl.BaseActionImpl;

/**
  * StockDetailAction
  */
  @Service(IStockDetailAction.ACTION_ID)
public class StockDetailActionImpl extends BaseActionImpl 
  implements IStockDetailAction {

    /**
      * 注入service
      */
    @Resource(name=IStockDetailService.SERVICE_ID)
	private IStockDetailService<StockDetailBO> stockDetailService;	
	
	/**
	  * 增加数据
	  */
	public void insertObject(Dto param) {
		logger.debug("<======StockDetailAction--addStockDetail======>");
		
		StockDetailBO stockDetailBO = BaseDto.toModel(StockDetailBO.class , param);
		stockDetailService.insertObject(stockDetailBO);
	}
	
	/**
      * 修改数据
      */
	public void updateObject(Dto param){
		logger.debug("<======StockDetailAction--updateStockDetail======>");
		
		StockDetailBO stockDetailBO = BaseDto.toModel(StockDetailBO.class , param);
		stockDetailService.updateObject(stockDetailBO);
	}

	 /**
      * 删除数据
      */
	public void deleteObject(Dto param) {
		logger.debug("<======StockDetailAction--deleteStockDetail======>");
		
		StockDetailBO stockDetailBO = BaseDto.toModel(StockDetailBO.class , param);
		stockDetailService.deleteObject(stockDetailBO);
	}
	
	/**
      * 查询单条数据
      */
	public Dto findOne(Dto param) {
		logger.debug("<======StockDetailAction--findOneStockDetail======>");
		
		StockDetailBO stockDetailBO = BaseDto.toModel(StockDetailBO.class , param);
		return stockDetailService.findOne(stockDetailBO).toDto();//返回的BO对象自动转换成Dto返回
	}  
	
	/**
      * 查询所有数据
      */
	public List<Dto> findAll(Dto param) {
		logger.debug("<======StockDetailAction--findAllStockDetail======>");
				
		return stockDetailService.findAllMap(param);
	} 
	
		
	 /**
      * 分页查询数据
      */
	public CurrentPage queryForPage(CurrentPage currentPage){
		logger.debug("<======StockDetailAction--queryStockDetailForPage======>");
		
		return stockDetailService.queryForPage(currentPage);
	}	
}
