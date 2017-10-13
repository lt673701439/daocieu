package com.taikang.udp.sys.action;

import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.core.action.IBaseAction;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;

import java.util.Arrays;
import java.util.List;


/**
  * IUserAction
  */

public interface IUserAction extends IBaseAction { 

	final String ACTION_ID = "userAction"; 	
	
	public CurrentPage queryByCondition(CurrentPage page);

	public void insertUser(Dto userDto);

	public List<Dto> queryAllUsersByCondition(Dto param);
}
