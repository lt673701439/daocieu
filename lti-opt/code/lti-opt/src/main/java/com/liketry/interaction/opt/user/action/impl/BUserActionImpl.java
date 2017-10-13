package com.liketry.interaction.opt.user.action.impl;


import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import com.liketry.interaction.opt.user.model.BUserBO;
import com.liketry.interaction.opt.user.action.IBUserAction;
import javax.annotation.Resource;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.liketry.interaction.opt.user.service.IBUserService;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import com.taikang.udp.framework.core.action.impl.BaseActionImpl;

/**
  * BUserAction
  */
  @Service(IBUserAction.ACTION_ID)
public class BUserActionImpl extends BaseActionImpl 
  implements IBUserAction {

    /**
      * 注入service
      */
    @Resource(name=IBUserService.SERVICE_ID)
	private IBUserService<BUserBO> bUserService;	
	
	/**
	  * 增加数据
	  */
	public void insertObject(Dto param) {
		logger.debug("<======BUserAction--addBUser======>");
		
		BUserBO bUserBO = BaseDto.toModel(BUserBO.class , param);
		bUserService.insertObject(bUserBO);
	}
	
	/**
      * 修改数据
      */
	public void updateObject(Dto param){
		logger.debug("<======BUserAction--updateBUser======>");
		
		BUserBO bUserBO = BaseDto.toModel(BUserBO.class , param);
		bUserService.updateObject(bUserBO);
	}

	 /**
      * 删除数据
      */
	public void deleteObject(Dto param) {
		logger.debug("<======BUserAction--deleteBUser======>");
		
		BUserBO bUserBO = BaseDto.toModel(BUserBO.class , param);
		bUserService.deleteObject(bUserBO);
	}
	
	/**
      * 查询单条数据
      */
	public Dto findOne(Dto param) {
		logger.debug("<======BUserAction--findOneBUser======>");
		
		BUserBO bUserBO = BaseDto.toModel(BUserBO.class , param);
		return bUserService.findOne(bUserBO).toDto();//返回的BO对象自动转换成Dto返回
	}  
	
	/**
      * 查询所有数据
      */
	public List<Dto> findAll(Dto param) {
		logger.debug("<======BUserAction--findAllBUser======>");
				
		return bUserService.findAllMap(param);
	} 
	
		
	 /**
      * 分页查询数据
      */
	public CurrentPage queryForPage(CurrentPage currentPage){
		logger.debug("<======BUserAction--queryBUserForPage======>");
		
		return bUserService.queryForPage(currentPage);
	}	
}
