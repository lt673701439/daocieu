package com.liketry.interaction.opt.benison.action.impl;


import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.liketry.interaction.opt.benison.model.BenisonBO;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import com.liketry.interaction.opt.benison.action.IBenisonAction;
import com.taikang.udp.framework.core.action.impl.BaseActionImpl;
import com.liketry.interaction.opt.benison.service.IBenisonService;

/**
  * BenisonAction
  */
  @Service(IBenisonAction.ACTION_ID)
public class BenisonActionImpl extends BaseActionImpl 
  implements IBenisonAction {

    /**
      * 注入service
      */
    @Resource(name=IBenisonService.SERVICE_ID)
	private IBenisonService<BenisonBO> benisonService;	
	
	/**
	  * 增加数据
	  */
	public void insertObject(Dto param) {
		logger.debug("<======BenisonAction--addBenison======>");
		
		BenisonBO benisonBO = BaseDto.toModel(BenisonBO.class , param);
		benisonService.insertObject(benisonBO);
	}
	
	/**
      * 修改数据
      */
	public void updateObject(Dto param){
		logger.debug("<======BenisonAction--updateBenison======>");
		
		BenisonBO benisonBO = BaseDto.toModel(BenisonBO.class , param);
		benisonService.updateObject(benisonBO);
	}

	 /**
      * 删除数据
      */
	public void deleteObject(Dto param) {
		logger.debug("<======BenisonAction--deleteBenison======>");
		
		BenisonBO benisonBO = BaseDto.toModel(BenisonBO.class , param);
		benisonService.deleteObject(benisonBO);
	}
	
	/**
      * 查询单条数据
      */
	public Dto findOne(Dto param) {
		logger.debug("<======BenisonAction--findOneBenison======>");
		
		BenisonBO benisonBO = BaseDto.toModel(BenisonBO.class , param);
		return benisonService.findOne(benisonBO).toDto();//返回的BO对象自动转换成Dto返回
	}  
	
	/**
      * 查询所有数据
      */
	public List<Dto> findAll(Dto param) {
		logger.debug("<======BenisonAction--findAllBenison======>");
				
		return benisonService.findAllMap(param);
	} 
	
		
	 /**
      * 分页查询数据
      */
	public CurrentPage queryForPage(CurrentPage currentPage){
		logger.debug("<======BenisonAction--queryBenisonForPage======>");
		
		return benisonService.queryForPage(currentPage);
	}	
	
	/**
     * 根据是否有关联祝福语数据
     */
	public List<Dto> findAllBySpotId(Dto param) {
		logger.debug("<======BenisonAction--findAllBySpotId======>");
				
		return benisonService.findAllBySpotId(param);
	} 
}
