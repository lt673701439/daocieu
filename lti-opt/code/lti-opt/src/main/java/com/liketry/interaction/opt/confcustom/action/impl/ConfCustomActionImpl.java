package com.liketry.interaction.opt.confcustom.action.impl;


import java.util.Arrays;
import java.util.List;
import com.liketry.interaction.opt.confcustom.model.ConfCustomBO;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import com.liketry.interaction.opt.confcustom.service.IConfCustomService;
import com.taikang.udp.framework.core.action.impl.BaseActionImpl;
import com.liketry.interaction.opt.confcustom.action.IConfCustomAction;

/**
  * ConfCustomAction
  */
  @Service(IConfCustomAction.ACTION_ID)
public class ConfCustomActionImpl extends BaseActionImpl 
  implements IConfCustomAction {

    /**
      * 注入service
      */
    @Resource(name=IConfCustomService.SERVICE_ID)
	private IConfCustomService<ConfCustomBO> confCustomService;	
	
	/**
	  * 增加数据
	  */
	public void insertObject(Dto param) {
		logger.debug("<======ConfCustomAction--addConfCustom======>");
		
		ConfCustomBO confCustomBO = BaseDto.toModel(ConfCustomBO.class , param);
		confCustomService.insertObject(confCustomBO);
	}
	
	/**
      * 修改数据
      */
	public void updateObject(Dto param){
		logger.debug("<======ConfCustomAction--updateConfCustom======>");
		
		ConfCustomBO confCustomBO = BaseDto.toModel(ConfCustomBO.class , param);
		confCustomService.updateObject(confCustomBO);
	}

	 /**
      * 删除数据
      */
	public void deleteObject(Dto param) {
		logger.debug("<======ConfCustomAction--deleteConfCustom======>");
		
		ConfCustomBO confCustomBO = BaseDto.toModel(ConfCustomBO.class , param);
		confCustomService.deleteObject(confCustomBO);
	}
	
	/**
      * 查询单条数据
      */
	public Dto findOne(Dto param) {
		logger.debug("<======ConfCustomAction--findOneConfCustom======>");
		
		ConfCustomBO confCustomBO = BaseDto.toModel(ConfCustomBO.class , param);
		return confCustomService.findOne(confCustomBO).toDto();//返回的BO对象自动转换成Dto返回
	}  
	
	/**
      * 查询所有数据
      */
	public List<Dto> findAll(Dto param) {
		logger.debug("<======ConfCustomAction--findAllConfCustom======>");
				
		return confCustomService.findAllMap(param);
	} 
	
		
	 /**
      * 分页查询数据
      */
	public CurrentPage queryForPage(CurrentPage currentPage){
		logger.debug("<======ConfCustomAction--queryConfCustomForPage======>");
		
		return confCustomService.queryForPage(currentPage);
	}	
}
