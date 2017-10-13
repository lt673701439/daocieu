package com.liketry.interaction.opt.commoditysku.action.impl;


import com.liketry.interaction.opt.commoditysku.model.CommoditySkuBO;
import java.util.Arrays;
import java.util.List;
import com.liketry.interaction.opt.commoditysku.action.ICommoditySkuAction;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import com.liketry.interaction.opt.commoditysku.service.ICommoditySkuService;
import com.taikang.udp.framework.core.action.impl.BaseActionImpl;

/**
  * CommoditySkuAction
  */
  @Service(ICommoditySkuAction.ACTION_ID)
public class CommoditySkuActionImpl extends BaseActionImpl 
  implements ICommoditySkuAction {

    /**
      * 注入service
      */
    @Resource(name=ICommoditySkuService.SERVICE_ID)
	private ICommoditySkuService<CommoditySkuBO> commoditySkuService;	
	
	/**
	  * 增加数据
	  */
	public void insertObject(Dto param) {
		logger.debug("<======CommoditySkuAction--addCommoditySku======>");
		
		CommoditySkuBO commoditySkuBO = BaseDto.toModel(CommoditySkuBO.class , param);
		commoditySkuService.insertObject(commoditySkuBO);
	}
	
	/**
      * 修改数据
      */
	public void updateObject(Dto param){
		logger.debug("<======CommoditySkuAction--updateCommoditySku======>");
		
		CommoditySkuBO commoditySkuBO = BaseDto.toModel(CommoditySkuBO.class , param);
		commoditySkuService.updateObject(commoditySkuBO);
	}

	 /**
      * 删除数据
      */
	public void deleteObject(Dto param) {
		logger.debug("<======CommoditySkuAction--deleteCommoditySku======>");
		
		CommoditySkuBO commoditySkuBO = BaseDto.toModel(CommoditySkuBO.class , param);
		commoditySkuService.deleteObject(commoditySkuBO);
	}
	
	/**
      * 查询单条数据
      */
	public Dto findOne(Dto param) {
		logger.debug("<======CommoditySkuAction--findOneCommoditySku======>");
		
		CommoditySkuBO commoditySkuBO = BaseDto.toModel(CommoditySkuBO.class , param);
		return commoditySkuService.findOne(commoditySkuBO).toDto();//返回的BO对象自动转换成Dto返回
	}  
	
	/**
      * 查询所有数据
      */
	public List<Dto> findAll(Dto param) {
		logger.debug("<======CommoditySkuAction--findAllCommoditySku======>");
				
		return commoditySkuService.findAllMap(param);
	} 
	
		
	 /**
      * 分页查询数据
      */
	public CurrentPage queryForPage(CurrentPage currentPage){
		logger.debug("<======CommoditySkuAction--queryCommoditySkuForPage======>");
		
		return commoditySkuService.queryForPage(currentPage);
	}
	
	/**
     * 查询最新的商品sku
     */
	public Dto findLastOne(Dto param) {
		logger.debug("<======CommoditySkuAction--findLastOne======>");
		
		CommoditySkuBO commoditySkuBO = BaseDto.toModel(CommoditySkuBO.class , param);
		return commoditySkuService.findLastOne(commoditySkuBO).toDto();//返回的BO对象自动转换成Dto返回
	}  
}
