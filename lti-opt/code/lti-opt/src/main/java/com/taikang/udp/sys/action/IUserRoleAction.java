package com.taikang.udp.sys.action;

import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.core.action.IBaseAction;

import java.util.Arrays;


/**
  * IUserRoleAction
  */

public interface IUserRoleAction extends IBaseAction { 

	final String ACTION_ID = "userRoleAction";
	
	/**
	 * 批量保存用户角色信息<br/>
	 * @param param   
	 * void
	 */
	public void batchSaveUserRole(Dto param);
}
