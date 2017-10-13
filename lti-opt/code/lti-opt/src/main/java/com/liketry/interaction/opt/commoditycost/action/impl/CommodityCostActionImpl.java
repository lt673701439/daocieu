package com.liketry.interaction.opt.commoditycost.action.impl;


import java.util.Arrays;
import java.util.List;
import com.liketry.interaction.opt.commoditycost.action.ICommodityCostAction;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import com.liketry.interaction.opt.commoditycost.service.ICommodityCostService;
import com.liketry.interaction.opt.commoditycost.model.CommodityCostBO;
import com.taikang.udp.framework.core.action.impl.BaseActionImpl;

/**
  * CommodityCostAction
  */
  @Service(ICommodityCostAction.ACTION_ID)
public class CommodityCostActionImpl extends BaseActionImpl 
  implements ICommodityCostAction {

    /**
      * 注入service
      */
    @Resource(name=ICommodityCostService.SERVICE_ID)
	private ICommodityCostService<CommodityCostBO> commodityCostService;	
	
	/**
	  * 增加数据
	  */
	public void insertObject(Dto param) {
		logger.debug("<======CommodityCostAction--addCommodityCost======>");
		
		CommodityCostBO commodityCostBO = BaseDto.toModel(CommodityCostBO.class , param);
		commodityCostService.insertObject(commodityCostBO);
	}
	
	/**
      * 修改数据
      */
	public void updateObject(Dto param){
		logger.debug("<======CommodityCostAction--updateCommodityCost======>");
		
		CommodityCostBO commodityCostBO = BaseDto.toModel(CommodityCostBO.class , param);
		commodityCostService.updateObject(commodityCostBO);
	}

	 /**
      * 删除数据
      */
	public void deleteObject(Dto param) {
		logger.debug("<======CommodityCostAction--deleteCommodityCost======>");
		
		CommodityCostBO commodityCostBO = BaseDto.toModel(CommodityCostBO.class , param);
		commodityCostService.deleteObject(commodityCostBO);
	}
	
	/**
      * 查询单条数据
      */
	public Dto findOne(Dto param) {
		logger.debug("<======CommodityCostAction--findOneCommodityCost======>");
		
		CommodityCostBO commodityCostBO = BaseDto.toModel(CommodityCostBO.class , param);
		return commodityCostService.findOne(commodityCostBO).toDto();//返回的BO对象自动转换成Dto返回
	}  
	
	/**
      * 查询所有数据
      */
	public List<Dto> findAll(Dto param) {
		logger.debug("<======CommodityCostAction--findAllCommodityCost======>");
				
		return commodityCostService.findAllMap(param);
	} 
	
		
	 /**
      * 分页查询数据
      */
	public CurrentPage queryForPage(CurrentPage currentPage){
		logger.debug("<======CommodityCostAction--queryCommodityCostForPage======>");
		
		return commodityCostService.queryForPage(currentPage);
	}	
}
