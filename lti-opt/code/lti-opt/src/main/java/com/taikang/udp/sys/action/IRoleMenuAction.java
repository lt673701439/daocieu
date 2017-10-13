package com.taikang.udp.sys.action;

import java.util.List;
import java.util.Map;

import com.taikang.udp.framework.core.action.IBaseAction;


/**
  * IRoleMenuAction
  */

public interface IRoleMenuAction extends IBaseAction { 

	final String ACTION_ID = "roleMenuAction";
	
 	/**
     * 批量增加数据
     */
	public boolean batchSaveRoleMenu(String roleId, String loginuser, String menuIdList);
}
