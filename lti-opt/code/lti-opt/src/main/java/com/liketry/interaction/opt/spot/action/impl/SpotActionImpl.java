package com.liketry.interaction.opt.spot.action.impl;


import java.util.Arrays;
import java.util.List;
import com.liketry.interaction.opt.spot.model.SpotBO;
import org.springframework.stereotype.Service;
import com.liketry.interaction.opt.spot.service.ISpotService;
import javax.annotation.Resource;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import com.liketry.interaction.opt.spot.action.ISpotAction;
import com.taikang.udp.framework.core.action.impl.BaseActionImpl;

/**
  * SpotAction
  */
  @Service(ISpotAction.ACTION_ID)
public class SpotActionImpl extends BaseActionImpl 
  implements ISpotAction {

    /**
      * 注入service
      */
    @Resource(name=ISpotService.SERVICE_ID)
	private ISpotService<SpotBO> spotService;	
	
	/**
	  * 增加数据
	  */
	public void insertObject(Dto param) {
		logger.debug("<======SpotAction--addSpot======>");
		
		SpotBO spotBO = BaseDto.toModel(SpotBO.class , param);
		spotService.insertObject(spotBO);
	}
	
	/**
      * 修改数据
      */
	public void updateObject(Dto param){
		logger.debug("<======SpotAction--updateSpot======>");
		
		SpotBO spotBO = BaseDto.toModel(SpotBO.class , param);
		spotService.updateObject(spotBO);
	}

	 /**
      * 删除数据
      */
	public void deleteObject(Dto param) {
		logger.debug("<======SpotAction--deleteSpot======>");
		
		SpotBO spotBO = BaseDto.toModel(SpotBO.class , param);
		spotService.deleteObject(spotBO);
	}
	
	/**
      * 查询单条数据
      */
	public Dto findOne(Dto param) {
		logger.debug("<======SpotAction--findOneSpot======>");
		
		SpotBO spotBO = BaseDto.toModel(SpotBO.class , param);
		return spotService.findOne(spotBO).toDto();//返回的BO对象自动转换成Dto返回
	}  
	
	/**
      * 查询所有数据
      */
	public List<Dto> findAll(Dto param) {
		logger.debug("<======SpotAction--findAllSpot======>");
				
		return spotService.findAllMap(param);
	} 
	
		
	 /**
      * 分页查询数据
      */
	public CurrentPage queryForPage(CurrentPage currentPage){
		logger.debug("<======SpotAction--querySpotForPage======>");
		
		return spotService.queryForPage(currentPage);
	}	
}
