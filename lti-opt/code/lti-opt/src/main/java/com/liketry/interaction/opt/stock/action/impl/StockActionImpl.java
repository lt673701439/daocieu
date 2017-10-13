package com.liketry.interaction.opt.stock.action.impl;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.http.util.TextUtils;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.liketry.interaction.opt.orderdetail.service.IOrderDetailService;
import com.liketry.interaction.opt.stock.action.IStockAction;
import com.liketry.interaction.opt.stock.model.StockBO;
import com.liketry.interaction.opt.stock.service.IStockService;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import com.taikang.udp.framework.core.action.impl.BaseActionImpl;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;

/**
  * StockAction
  */
  @Service(IStockAction.ACTION_ID)
public class StockActionImpl extends BaseActionImpl 
  implements IStockAction {

    /**
      * 注入service
      */
    @Resource(name=IStockService.SERVICE_ID)
	private IStockService<StockBO> stockService;
    
    /**
     * 注入service
     */
   @Resource(name=IOrderDetailService.SERVICE_ID)
	private IOrderDetailService<StockBO> orderDetailService;
	
	/**
	  * 增加数据
	  */
	public void insertObject(Dto param) {
		logger.debug("<======StockAction--addStock======>");
		
		StockBO stockBO = BaseDto.toModel(StockBO.class , param);
		stockService.insertObject(stockBO);
	}
	
	/**
      * 修改数据
      */
	public void updateObject(Dto param){
		logger.debug("<======StockAction--updateStock======>");
		
		StockBO stockBO = BaseDto.toModel(StockBO.class , param);
		stockService.updateObject(stockBO);
	}

	 /**
      * 删除数据
      */
	public void deleteObject(Dto param) {
		logger.debug("<======StockAction--deleteStock======>");
		
		StockBO stockBO = BaseDto.toModel(StockBO.class , param);
		stockService.deleteObject(stockBO);
	}
	
	/**
      * 查询单条数据
      */
	public Dto findOne(Dto param) {
		logger.debug("<======StockAction--findOneStock======>");
		
		StockBO stockBO = BaseDto.toModel(StockBO.class , param);
		return stockService.findOne(stockBO).toDto();//返回的BO对象自动转换成Dto返回
	}  
	
	/**
      * 查询所有数据
      */
	public List<Dto> findAll(Dto param) {
		logger.debug("<======StockAction--findAllStock======>");
				
		return stockService.findAllMap(param);
	} 
	
		
	 /**
      * 分页查询数据
      */
	public CurrentPage queryForPage(CurrentPage currentPage){
		logger.debug("<======StockAction--queryStockForPage======>");
		
		return stockService.queryForPage(currentPage);
	}	
	
	/**
     * 查询所有数据
     */
	public List<StockBO> findAllByOrderId(Dto param) {
		logger.debug("<======StockAction--findAllByOrderId======>");
		
		List<Dto> orderDetailList = orderDetailService.findAllMap(param);
		List<StockBO> stockList = new ArrayList<StockBO>();
		if(orderDetailList!=null && orderDetailList.size()>0){
			orderDetailList.forEach(orderDetail->{
				//查询商品是否有库存
				if(!TextUtils.isBlank(orderDetail.getAsString("commodity_id"))){
					//查询商品是否有库存
					Dto param2 = new BaseDto();
					param2.put("commodityId", orderDetail.getAsString("commodity_id"));
					param2.put("stockDate", orderDetail.getAsString("play_date"));
					StockBO stockBO = BaseDto.toModel(StockBO.class , param2);
					StockBO stock = stockService.findStockByCommodityId(stockBO);
					if(stock!=null){
						stockList.add(stock);
					}else{
						logger.error("<====commodityCode:{} have not stock!!============>",orderDetail.getAsString("commodity_code"));
					}
				}
			});
		}		
		return stockList;
	} 
}
