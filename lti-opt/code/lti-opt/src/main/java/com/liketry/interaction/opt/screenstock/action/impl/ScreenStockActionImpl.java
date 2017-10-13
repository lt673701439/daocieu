package com.liketry.interaction.opt.screenstock.action.impl;


import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import com.liketry.interaction.opt.screenstock.action.IScreenStockAction;
import com.liketry.interaction.opt.screenstock.model.ScreenStockBO;
import com.taikang.udp.framework.core.action.impl.BaseActionImpl;
import com.liketry.interaction.opt.screenstock.service.IScreenStockService;

/**
  * ScreenStockAction
  */
  @Service(IScreenStockAction.ACTION_ID)
public class ScreenStockActionImpl extends BaseActionImpl 
  implements IScreenStockAction {

    /**
      * 注入service
      */
    @Resource(name=IScreenStockService.SERVICE_ID)
	private IScreenStockService<ScreenStockBO> screenStockService;	
	
	/**
	  * 增加数据
	  */
	public void insertObject(Dto param) {
		logger.debug("<======ScreenStockAction--addScreenStock======>");
		
		ScreenStockBO screenStockBO = BaseDto.toModel(ScreenStockBO.class , param);
		screenStockService.insertObject(screenStockBO);
	}
	
	/**
      * 修改数据
      */
	public void updateObject(Dto param){
		logger.debug("<======ScreenStockAction--updateScreenStock======>");
		
		ScreenStockBO screenStockBO = BaseDto.toModel(ScreenStockBO.class , param);
		screenStockService.updateObject(screenStockBO);
	}

	 /**
      * 删除数据
      */
	public void deleteObject(Dto param) {
		logger.debug("<======ScreenStockAction--deleteScreenStock======>");
		
		ScreenStockBO screenStockBO = BaseDto.toModel(ScreenStockBO.class , param);
		screenStockService.deleteObject(screenStockBO);
	}
	
	/**
      * 查询单条数据
      */
	public Dto findOne(Dto param) {
		logger.debug("<======ScreenStockAction--findOneScreenStock======>");
		
		ScreenStockBO screenStockBO = BaseDto.toModel(ScreenStockBO.class , param);
		return screenStockService.findOne(screenStockBO).toDto();//返回的BO对象自动转换成Dto返回
	}  
	
	/**
      * 查询所有数据
      */
	public List<Dto> findAll(Dto param) {
		logger.debug("<======ScreenStockAction--findAllScreenStock======>");
				
		return screenStockService.findAllMap(param);
	} 
	
		
	 /**
      * 分页查询数据
      */
	public CurrentPage queryForPage(CurrentPage currentPage){
		logger.debug("<======ScreenStockAction--queryScreenStockForPage======>");
		
		return screenStockService.queryForPage(currentPage);
	}	
	
	/**
     * 查询是否存在有时间冲突的屏幕
     */
	public List<Dto> findRepeatByDateAndTime(Dto param) {
		logger.debug("<======ScreenStockAction--findRepeatByDateAndTime======>");
				
		return screenStockService.findRepeatByDateAndTime(param);
	} 
}
