package com.taikang.udp.sys.action.impl;


import com.taikang.udp.framework.common.datastructre.impl.BaseDto;

import javax.annotation.Resource;

import com.taikang.udp.sys.action.IComAction;

import java.util.List;

import com.taikang.udp.sys.service.IComService;
import com.taikang.udp.sys.util.vo.Tree;
import com.taikang.udp.framework.core.action.impl.BaseActionImpl;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;

import java.util.Arrays;

import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.sys.model.ComBO;

import org.springframework.stereotype.Service;

/**
  * ComAction
  */
  @Service(IComAction.ACTION_ID)
public class ComActionImpl extends BaseActionImpl 
  implements IComAction {

    /**
      * 注入service
      */
    @Resource(name=IComService.SERVICE_ID)
	private IComService<ComBO> comService;	
	
	/**
	  * 增加数据
	  */
	public void insertObject(Dto param) {
		logger.debug("<======ComAction--addCom======>");
		
		ComBO comBO = BaseDto.toModel(ComBO.class , param);
		comService.insertObject(comBO);
	}
	
	/**
      * 修改数据
      */
	public void updateObject(Dto param){
		logger.debug("<======ComAction--updateCom======>");
		
		ComBO comBO = BaseDto.toModel(ComBO.class , param);
		comService.updateObject(comBO);
	}

	 /**
      * 删除数据
      */
	public void deleteObject(Dto param) {
		logger.debug("<======ComAction--deleteCom======>");
		
		ComBO comBO = BaseDto.toModel(ComBO.class , param);
		comService.deleteObject(comBO);
	}
	
	/**
      * 查询单条数据
      */
	public Dto findOne(Dto param) {
		logger.debug("<======ComAction--findOneCom======>");
		
		ComBO comBO = BaseDto.toModel(ComBO.class , param);
		return comService.findOne(comBO).toDto();//返回的BO对象自动转换成Dto返回
	}  
	
	/**
      * 查询所有数据
      */
	public List<Dto> findAll(Dto param) {
		logger.debug("<======ComAction--findAllCom======>");
				
		return comService.findAllMap(param);
	} 
	
		
	 /**
      * 分页查询数据
      */
	public CurrentPage queryForPage(CurrentPage currentPage){
		logger.debug("<======ComAction--queryComForPage======>");
		
		return comService.queryForPage(currentPage);
	}	
	
	/**
	 * 获取机构树信息<br/>
	 * @return   
	 * List<Tree>
	 */
	public List<Tree> getComInfo() {
		logger.debug("<======ComAction--getComInfo======>");
		
		return comService.getComInfo();
	}
	
	/**
	 * 获取指定机构的子机构信息<br/>
	 * @param comId
	 * @return   
	 * List<Tree>
	 */
	public List<Tree> getChildrenTreeLst(Integer comId, String state) {
		logger.debug("<======ComAction--getChildrenTreeLst======>");
		
		return comService.getChildrenTreeLst(comId,state);
	}
	
	/**
	 * 获取指定机构的子机构信息<br/>
	 * @param comId
	 * @return   
	 * List<Tree>
	 */
	public CurrentPage getChildList(CurrentPage currentPage) {
		logger.debug("<======ComAction--getChildList======>");
		
		return comService.getChildList(currentPage);
	}
	
	/**
	 * 保存机构数据<br/>
	 * @param param   
	 * void
	 */
	public void savwObject(Dto param) {
		logger.debug("<======ComAction--savwObject======>");
		
		ComBO comBO = BaseDto.toModel(ComBO.class , param);
		comService.savwObject(param);
	}
	
	/**
	 * 按条件分页查询机构数据<br/>
	 * @param currentPage
	 * @return CurrentPage
	 */
	public CurrentPage getByconlist (CurrentPage currentPage) {
		logger.debug("<======ComAction--getByconlist======>");
		
		return comService.getByconlist(currentPage);
	}
}
