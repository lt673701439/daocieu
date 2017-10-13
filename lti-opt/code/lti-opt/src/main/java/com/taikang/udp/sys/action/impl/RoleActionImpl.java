package com.taikang.udp.sys.action.impl;


import com.taikang.udp.framework.common.datastructre.impl.BaseDto;

import javax.annotation.Resource;

import com.taikang.udp.sys.model.RoleBO;
import com.taikang.udp.sys.action.IRoleAction;

import java.util.List;

import com.taikang.udp.framework.core.action.impl.BaseActionImpl;
import com.taikang.udp.framework.core.exception.app.TKBizException;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;

import java.util.Arrays;

import com.taikang.udp.framework.common.datastructre.Dto;

import org.springframework.stereotype.Service;

import com.taikang.udp.sys.service.IRoleService;

/**
 * 
 *  处理角色相关<br/>
 * @author duyq06
 * @version [版本号，默认V1.0.0]
 * @Credited 2014年12月24日 下午5:38:24
 * @see       [相关类/方法]
 * @since     [产品/模块版本]
 */
  @Service(IRoleAction.ACTION_ID)
public class RoleActionImpl extends BaseActionImpl 
  implements IRoleAction {

    /**
     * 注入service
     */
    @Resource(name=IRoleService.SERVICE_ID)
	private IRoleService<RoleBO> roleService;	
	
	/**
	  * 增加数据
	  */
	public void insertObject(Dto param) {
		logger.debug("<======RoleAction--addRole======>");
		
		RoleBO roleBO = BaseDto.toModel(RoleBO.class , param);
		roleService.insertObject(roleBO);
	}
	
	/**
      * 修改数据
      */
	public void updateObject(Dto param){
		logger.debug("<======RoleAction--updateRole======>");
		
		RoleBO roleBO = BaseDto.toModel(RoleBO.class , param);
		roleService.updateObject(roleBO);
	}

	 /**
      * 删除数据
      */
	public void deleteObject(Dto param) {
		logger.debug("<======RoleAction--deleteRole======>");
		
		RoleBO roleBO = BaseDto.toModel(RoleBO.class , param);
		roleService.deleteObject(roleBO);
	}
	
	/**
      * 查询单条数据
      */
	public Dto findOne(Dto param) {
		logger.debug("<======RoleAction--findOneRole======>");
		
		RoleBO roleBO = BaseDto.toModel(RoleBO.class , param);
		return roleService.findOne(roleBO).toDto();//返回的BO对象自动转换成Dto返回
	}  
	
	/**
      * 查询所有数据
      */
	public List<Dto> findAll(Dto param) {
		logger.debug("<======RoleAction--findAllRole======>");
				
		return roleService.findAllMap(param);
	} 
	
		
	 /**
      * 分页查询数据
      */
	public CurrentPage queryForPage(CurrentPage currentPage){
		logger.debug("<======RoleAction--queryRoleForPage======>");
		
		return roleService.queryForPage(currentPage);
	}

	/**
	 * 查询指定用户是否拥有角色信息列表<br/>
	 * @param 
	 * @return
	 * @throws TKBizException
	 */
    public List<Dto> getUsersRoleList(Dto param) throws TKBizException {
    	logger.debug("<======RoleAction--getUsersRoleList======>");
		return roleService.getUsersRoleList(param);
    }

	public Dto findRoleIdByCode(Dto param) {
		logger.debug("<======RoleAction--findRoleIdByCode======>");
		
		RoleBO roleBO = BaseDto.toModel(RoleBO.class , param);
		return roleService.findRoleIdByCode(roleBO).toDto();//返回的BO对象自动转换成Dto返回
	}	
}
