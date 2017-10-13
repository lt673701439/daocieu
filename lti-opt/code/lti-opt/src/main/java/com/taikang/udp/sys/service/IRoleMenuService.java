package com.taikang.udp.sys.service;

import com.taikang.udp.framework.core.service.IBaseService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
  * IRoleMenuService
  */
 
 public interface IRoleMenuService<T> extends IBaseService<T>{
 
 	final String SERVICE_ID = "roleMenuService";
 	
 	public boolean batchSaveRoleMenu(String roleId, String loginuser, String menuIdList);
 }
 
 
 