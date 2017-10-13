package com.taikang.udp.sys.model;

import java.sql.Timestamp;
import java.lang.String;
import java.lang.Integer;
import java.util.Arrays;
import com.taikang.udp.framework.common.datastructre.impl.BaseBO;


/**
  * RoleBO 
  */
public class RoleBO  extends BaseBO {
	
	private static final long serialVersionUID = 1L;
	
	public RoleBO(){
		init();
	}
	
	protected void init(){
		super.init();
		this.addList(Arrays.asList("role_id","role_code","role_name","add_prop","role_status","role_nature","backup1","backup2","creator","create_time","last_modby","last_modtime"));
	}
	
		
		
	private Integer roleId;
		public void setRoleId(Integer roleId){
		getData().put("role_id",roleId);
		this.roleId=roleId;
	}
	
	public Integer getRoleId(){
		return (Integer)getData().get("role_id");
	}	
		
		
	private String roleCode;
		public void setRoleCode(String roleCode){
		getData().put("role_code",roleCode);
		this.roleCode=roleCode;
	}
	
	public String getRoleCode(){
		return (String)getData().get("role_code");
	}	
		
		
	private String roleName;
		public void setRoleName(String roleName){
		getData().put("role_name",roleName);
		this.roleName=roleName;
	}
	
	public String getRoleName(){
		return (String)getData().get("role_name");
	}	
		
		
	private String addProp;
		public void setAddProp(String addProp){
		getData().put("add_prop",addProp);
		this.addProp=addProp;
	}
	
	public String getAddProp(){
		return (String)getData().get("add_prop");
	}	
		
		
	private String roleStatus;
		public void setRoleStatus(String roleStatus){
		getData().put("role_status",roleStatus);
		this.roleStatus=roleStatus;
	}
	
	public String getRoleStatus(){
		return (String)getData().get("role_status");
	}	
		
		
	private String roleNature;
		public void setRoleNature(String roleNature){
		getData().put("role_nature",roleNature);
		this.roleNature=roleNature;
	}
	
	public String getRoleNature(){
		return (String)getData().get("role_nature");
	}	
		
		
	private String backup1;
		public void setBackup1(String backup1){
		getData().put("backup1",backup1);
		this.backup1=backup1;
	}
	
	public String getBackup1(){
		return (String)getData().get("backup1");
	}	
		
		
	private String backup2;
		public void setBackup2(String backup2){
		getData().put("backup2",backup2);
		this.backup2=backup2;
	}
	
	public String getBackup2(){
		return (String)getData().get("backup2");
	}	
		
		
	private Integer creator;
		public void setCreator(Integer creator){
		getData().put("creator",creator);
		this.creator=creator;
	}
	
	public Integer getCreator(){
		return (Integer)getData().get("creator");
	}	
		
		
	private Timestamp createTime;
		public void setCreateTime(Timestamp createTime){
		getData().put("create_time",createTime);
		this.createTime=createTime;
	}
	
	public Timestamp getCreateTime(){
		return (Timestamp)getData().get("create_time");
	}	
		
		
	private Integer lastModby;
		public void setLastModby(Integer lastModby){
		getData().put("last_modby",lastModby);
		this.lastModby=lastModby;
	}
	
	public Integer getLastModby(){
		return (Integer)getData().get("last_modby");
	}	
		
		
	private Timestamp lastModtime;
		public void setLastModtime(Timestamp lastModtime){
		getData().put("last_modtime",lastModtime);
		this.lastModtime=lastModtime;
	}
	
	public Timestamp getLastModtime(){
		return (Timestamp)getData().get("last_modtime");
	}	
	 }

