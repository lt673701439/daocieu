package com.taikang.udp.sys.service;

import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.common.datastructre.impl.BaseBO;
import com.taikang.udp.framework.core.exception.app.TKBizException;
import com.taikang.udp.framework.core.service.IBaseService;
import com.taikang.udp.sys.model.RoleBO;

import java.util.Arrays;
import java.util.List;

/**
  * IRoleService
  */
 
 public interface IRoleService<T> extends IBaseService<T>{
 
 	final String SERVICE_ID = "roleService";
 	
	public List<Dto> getUsersRoleList(Dto param);

	public BaseBO findRoleIdByCode(RoleBO roleBO);
 }
 
 
 