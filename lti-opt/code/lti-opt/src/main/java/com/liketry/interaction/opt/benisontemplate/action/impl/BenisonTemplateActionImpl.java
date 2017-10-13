package com.liketry.interaction.opt.benisontemplate.action.impl;


import java.util.Arrays;
import java.util.List;
import com.liketry.interaction.opt.benisontemplate.action.IBenisonTemplateAction;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import com.liketry.interaction.opt.benisontemplate.service.IBenisonTemplateService;
import com.taikang.udp.framework.core.action.impl.BaseActionImpl;
import com.liketry.interaction.opt.benisontemplate.model.BenisonTemplateBO;

/**
  * BenisonTemplateAction
  */
  @Service(IBenisonTemplateAction.ACTION_ID)
public class BenisonTemplateActionImpl extends BaseActionImpl 
  implements IBenisonTemplateAction {

    /**
      * 注入service
      */
    @Resource(name=IBenisonTemplateService.SERVICE_ID)
	private IBenisonTemplateService<BenisonTemplateBO> benisonTemplateService;	
	
	/**
	  * 增加数据
	  */
	public void insertObject(Dto param) {
		logger.debug("<======BenisonTemplateAction--addBenisonTemplate======>");
		
		BenisonTemplateBO benisonTemplateBO = BaseDto.toModel(BenisonTemplateBO.class , param);
		benisonTemplateService.insertObject(benisonTemplateBO);
	}
	
	/**
      * 修改数据
      */
	public void updateObject(Dto param){
		logger.debug("<======BenisonTemplateAction--updateBenisonTemplate======>");
		
		BenisonTemplateBO benisonTemplateBO = BaseDto.toModel(BenisonTemplateBO.class , param);
		benisonTemplateService.updateObject(benisonTemplateBO);
	}

	 /**
      * 删除数据
      */
	public void deleteObject(Dto param) {
		logger.debug("<======BenisonTemplateAction--deleteBenisonTemplate======>");
		
		BenisonTemplateBO benisonTemplateBO = BaseDto.toModel(BenisonTemplateBO.class , param);
		benisonTemplateService.deleteObject(benisonTemplateBO);
	}
	
	/**
      * 查询单条数据
      */
	public Dto findOne(Dto param) {
		logger.debug("<======BenisonTemplateAction--findOneBenisonTemplate======>");
		
		BenisonTemplateBO benisonTemplateBO = BaseDto.toModel(BenisonTemplateBO.class , param);
		return benisonTemplateService.findOne(benisonTemplateBO).toDto();//返回的BO对象自动转换成Dto返回
	}  
	
	/**
      * 查询所有数据
      */
	public List<Dto> findAll(Dto param) {
		logger.debug("<======BenisonTemplateAction--findAllBenisonTemplate======>");
				
		return benisonTemplateService.findAllMap(param);
	} 
	
		
	 /**
      * 分页查询数据
      */
	public CurrentPage queryForPage(CurrentPage currentPage){
		logger.debug("<======BenisonTemplateAction--queryBenisonTemplateForPage======>");
		
		return benisonTemplateService.queryForPage(currentPage);
	}	
	
	/**
     * 根据祝福语ID查询是否有关联模板数据
     */
	public List<Dto> findAllByBenisonId(Dto param) {
		logger.debug("<======BenisonTemplateAction--findAllByBenisonId======>");
				
		return benisonTemplateService.findAllByBenisonId(param);
	}
	
	/**
     * 根据祝福语图片ID查询是否有关联模板数据
     */
	public List<Dto> findAllByImgId(Dto param) {
		logger.debug("<======BenisonTemplateAction--findAllByImgId======>");
				
		return benisonTemplateService.findAllByImgId(param);
	} 
	
	/**
     * 根据祝福语类型ID查询是否有关联模板数据
     */
	public List<Dto> findAllByTypeId(Dto param) {
		logger.debug("<======BenisonTemplateAction--findAllByTypeId======>");
				
		return benisonTemplateService.findAllByTypeId(param);
	} 
	
	/**
     * 根据屏幕ID查询是否有关联模板数据
     */
	public List<Dto> findAllByScreenId(Dto param) {
		logger.debug("<======BenisonTemplateAction--findAllByScreenId======>");
				
		return benisonTemplateService.findAllByScreenId(param);
	} 
	
	/**
     * 根据屏幕ID查询最新一条模板
     */
	public Dto findLastOne(Dto param) {
		logger.debug("<======BenisonTemplateAction--findLastOne======>");
		
		BenisonTemplateBO benisonTemplateBO = BaseDto.toModel(BenisonTemplateBO.class , param);
		return benisonTemplateService.findLastOne(benisonTemplateBO).toDto();//返回的BO对象自动转换成Dto返回
	}  
	
}
