package com.liketry.interaction.opt.benisontype.action.impl;


import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import com.liketry.interaction.opt.benisontype.service.IBenisonTypeService;
import javax.annotation.Resource;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import com.liketry.interaction.opt.benisontype.model.BenisonTypeBO;
import com.taikang.udp.framework.core.action.impl.BaseActionImpl;
import com.liketry.interaction.opt.benisontype.action.IBenisonTypeAction;

/**
  * BenisonTypeAction
  */
  @Service(IBenisonTypeAction.ACTION_ID)
public class BenisonTypeActionImpl extends BaseActionImpl 
  implements IBenisonTypeAction {

    /**
      * 注入service
      */
    @Resource(name=IBenisonTypeService.SERVICE_ID)
	private IBenisonTypeService<BenisonTypeBO> benisonTypeService;	
	
	/**
	  * 增加数据
	  */
	public void insertObject(Dto param) {
		logger.debug("<======BenisonTypeAction--addBenisonType======>");
		
		BenisonTypeBO benisonTypeBO = BaseDto.toModel(BenisonTypeBO.class , param);
		benisonTypeService.insertObject(benisonTypeBO);
	}
	
	/**
      * 修改数据
      */
	public void updateObject(Dto param){
		logger.debug("<======BenisonTypeAction--updateBenisonType======>");
		
		BenisonTypeBO benisonTypeBO = BaseDto.toModel(BenisonTypeBO.class , param);
		benisonTypeService.updateObject(benisonTypeBO);
	}

	 /**
      * 删除数据
      */
	public void deleteObject(Dto param) {
		logger.debug("<======BenisonTypeAction--deleteBenisonType======>");
		
		BenisonTypeBO benisonTypeBO = BaseDto.toModel(BenisonTypeBO.class , param);
		benisonTypeService.deleteObject(benisonTypeBO);
	}
	
	/**
      * 查询单条数据
      */
	public Dto findOne(Dto param) {
		logger.debug("<======BenisonTypeAction--findOneBenisonType======>");
		
		BenisonTypeBO benisonTypeBO = BaseDto.toModel(BenisonTypeBO.class , param);
		return benisonTypeService.findOne(benisonTypeBO).toDto();//返回的BO对象自动转换成Dto返回
	}  
	
	/**
      * 查询所有数据
      */
	public List<Dto> findAll(Dto param) {
		logger.debug("<======BenisonTypeAction--findAllBenisonType======>");
				
		return benisonTypeService.findAllMap(param);
	} 
	
		
	 /**
      * 分页查询数据
      */
	public CurrentPage queryForPage(CurrentPage currentPage){
		logger.debug("<======BenisonTypeAction--queryBenisonTypeForPage======>");
		
		return benisonTypeService.queryForPage(currentPage);
	}	
}
