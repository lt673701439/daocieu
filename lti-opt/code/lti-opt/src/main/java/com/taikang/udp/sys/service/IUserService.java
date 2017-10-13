package com.taikang.udp.sys.service;

import java.util.List;

import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.core.service.IBaseService;
import com.taikang.udp.sys.model.UserBO;

/**
  * IUserService
  */
 
 public interface IUserService<T> extends IBaseService<T>{
 
 	final String SERVICE_ID = "userService";  	
 	public CurrentPage queryByCondition(CurrentPage currentPage);
	public void insertUser(UserBO userBO);
	public List<Dto> queryAllUsersByCondition(Dto param);
 }
 
 
 