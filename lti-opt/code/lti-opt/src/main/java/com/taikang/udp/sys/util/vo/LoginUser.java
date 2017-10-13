package com.taikang.udp.sys.util.vo;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;
import com.taikang.udp.sys.model.RoleBO;
import com.taikang.udp.sys.model.UserBO;

public class LoginUser implements Serializable {

	private static final long serialVersionUID = -925350357539820864L;
	
	private UserBO user;
	
	private List<RoleBO> roleList = Lists.newArrayList(); // 拥有角色列表
	  
  	public LoginUser(UserBO user) {
		this.user = user;
	}
  	
  	public UserBO getUser(){
  		return this.user;
  	}
  	
  	//用户ID
  	public void setUserId(Integer userId){
		this.user.setUserId(userId);
	}
	
	public Integer getUserId(){
		return this.user.getUserId();
	}
	
	//用户登录名
	public void setUserCode(String userCode){
		this.user.setUserCode(userCode);
	}
	
	public String getUserCode(){
		return this.user.getUserCode();
	}
	
	//登陆密码
	public void setUserPwd(String userPwd){
		this.user.setUserPwd(userPwd);
	}
	
	public String getUserPwd(){
		return this.user.getUserPwd();
	}
	
	//用户姓名
	public void setUserName(String userName){
		this.user.setUserName(userName);
	}
	
	public String getUserName(){
		return this.user.getUserName();
	}
	
	/**
	 * 用户拥有的角色
	 * @return
	 */
	public List<RoleBO> getRoleList() {
		return roleList;
	}
	
	public void setRoleList(List<RoleBO> roleList) {
		this.roleList = roleList;
	}

	public List<Integer> getRoleIdList() {
		List<Integer> roleIdList = Lists.newArrayList();
		for (RoleBO role : roleList) {
			roleIdList.add(role.getRoleId());
		}
		return roleIdList;
	}

	public void setRoleIdList(List<Integer> roleIdList) {
		roleList = Lists.newArrayList();
		for (Integer roleId : roleIdList) {
			RoleBO role = new RoleBO();
			role.setRoleId(roleId);
			roleList.add(role);
		}
	}
}
