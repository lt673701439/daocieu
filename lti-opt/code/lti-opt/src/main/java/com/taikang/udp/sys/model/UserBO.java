package com.taikang.udp.sys.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.lang.String;
import java.lang.Integer;
import java.util.Arrays;
import com.taikang.udp.framework.common.datastructre.impl.BaseBO;

/**
 * UserBO
 */
public class UserBO extends BaseBO {

	private static final long serialVersionUID = 1L;

	public UserBO() {
		init();
	}

	protected void init() {
		super.init();
		this.addList(Arrays.asList("user_id","user_type", "user_code", "phone_num","user_pwd",
				"user_name", "user_email","email_pwd","err_num", "pwd_log", "modify_time",
				"start_validate", "end_validate", "user_nature", "user_dept",
				"user_com", "work_status", "user_status", "login_time",
				"is_login", "client_ip", "com_coy", "com_branch",
				"com_agenter", "backup1", "backup2", "creator", "create_time",
				"last_modby", "last_modtime","open_id","user_sex","web"));
	}

	private Integer userId;

	public void setUserId(Integer userId) {
		getData().put("user_id", userId);
		this.userId = userId;
	}

	public Integer getUserId() {
		return (Integer) getData().get("user_id");
	}
	
	private String userType;
	public void setUserType(String userType){
	getData().put("user_type",userType);
	this.userType=userType;
	}

	public String getUserType(){
	return (String)getData().get("user_type");
	}	
	
	private String userCode;

	public void setUserCode(String userCode) {
		getData().put("user_code", userCode);
		this.userCode = userCode;
	}

	public String getUserCode() {
		return (String) getData().get("user_code");
	}

	private String userPwd;

	public void setUserPwd(String userPwd) {
		getData().put("user_pwd", userPwd);
		this.userPwd = userPwd;
	}

	public String getUserPwd() {
		return (String) getData().get("user_pwd");
	}
	
	private String phoneNum;

	public void setPhoneNum(String phoneNum) {
		getData().put("phone_num", phoneNum);
		this.phoneNum = phoneNum;
	}

	public String getPhoneNum() {
		return (String) getData().get("phone_num");
	}

	private String userName;

	public void setUserName(String userName) {
		getData().put("user_name", userName);
		this.userName = userName;
	}

	public String getUserName() {
		return (String) getData().get("user_name");
	}
	
	private String userEmail;
	public void setUserEmail(String userEmail){
		getData().put("user_email",userEmail);
		this.userEmail=userEmail;
	}
	
	public String getUserEmail(){
		return (String)getData().get("user_email");
	}	
		
		
	private String emailPwd;
		public void setEmailPwd(String emailPwd){
		getData().put("email_pwd",emailPwd);
		this.emailPwd=emailPwd;
	}
	
	public String getEmailPwd(){
		return (String)getData().get("email_pwd");
	}	

	private Integer errNum;

	public void setErrNum(Integer errNum) {
		getData().put("err_num", errNum);
		this.errNum = errNum;
	}

	public Integer getErrNum() {
		return (Integer) getData().get("err_num");
	}

	private String pwdLog;

	public void setPwdLog(String pwdLog) {
		getData().put("pwd_log", pwdLog);
		this.pwdLog = pwdLog;
	}

	public String getPwdLog() {
		return (String) getData().get("pwd_log");
	}

	private Timestamp modifyTime;

	public void setModifyTime(Timestamp modifyTime) {
		getData().put("modify_time", modifyTime);
		this.modifyTime = modifyTime;
	}

	public Timestamp getModifyTime() {
		return (Timestamp) getData().get("modify_time");
	}

	private Date startValidate;

	public void setStartValidate(Date startValidate) {
		getData().put("start_validate", startValidate);
		this.startValidate = startValidate;
	}

	public Date getStartValidate() {
		return (Date) getData().get("start_validate");
	}

	private Date endValidate;

	public void setEndValidate(Date endValidate) {
		getData().put("end_validate", endValidate);
		this.endValidate = endValidate;
	}

	public Date getEndValidate() {
		return (Date) getData().get("end_validate");
	}

	private String userNature;

	public void setUserNature(String userNature) {
		getData().put("user_nature", userNature);
		this.userNature = userNature;
	}

	public String getUserNature() {
		return (String) getData().get("user_nature");
	}

	private String userDept;

	public void setUserDept(String userDept) {
		getData().put("user_dept", userDept);
		this.userDept = userDept;
	}

	public String getUserDept() {
		return (String) getData().get("user_dept");
	}

	private String userCom;

	public void setUserCom(String userCom) {
		getData().put("user_com", userCom);
		this.userCom = userCom;
	}

	public String getUserCom() {
		return (String) getData().get("user_com");
	}

	private String workStatus;

	public void setWorkStatus(String workStatus) {
		getData().put("work_status", workStatus);
		this.workStatus = workStatus;
	}

	public String getWorkStatus() {
		return (String) getData().get("work_status");
	}

	private String userStatus;

	public void setUserStatus(String userStatus) {
		getData().put("user_status", userStatus);
		this.userStatus = userStatus;
	}

	public String getUserStatus() {
		return (String) getData().get("user_status");
	}

	private Timestamp loginTime;

	public void setLoginTime(Timestamp loginTime) {
		getData().put("login_time", loginTime);
		this.loginTime = loginTime;
	}

	public Timestamp getLoginTime() {
		return (Timestamp) getData().get("login_time");
	}

	private String isLogin;

	public void setIsLogin(String isLogin) {
		getData().put("is_login", isLogin);
		this.isLogin = isLogin;
	}

	public String getIsLogin() {
		return (String) getData().get("is_login");
	}

	private String clientIp;

	public void setClientIp(String clientIp) {
		getData().put("client_ip", clientIp);
		this.clientIp = clientIp;
	}

	public String getClientIp() {
		return (String) getData().get("client_ip");
	}

	private String comCoy;

	public void setComCoy(String comCoy) {
		getData().put("com_coy", comCoy);
		this.comCoy = comCoy;
	}

	public String getComCoy() {
		return (String) getData().get("com_coy");
	}

	private String comBranch;

	public void setComBranch(String comBranch) {
		getData().put("com_branch", comBranch);
		this.comBranch = comBranch;
	}

	public String getComBranch() {
		return (String) getData().get("com_branch");
	}

	private String comAgenter;

	public void setComAgenter(String comAgenter) {
		getData().put("com_agenter", comAgenter);
		this.comAgenter = comAgenter;
	}

	public String getComAgenter() {
		return (String) getData().get("com_agenter");
	}

	private String backup1;

	public void setBackup1(String backup1) {
		getData().put("backup1", backup1);
		this.backup1 = backup1;
	}

	public String getBackup1() {
		return (String) getData().get("backup1");
	}

	private String backup2;

	public void setBackup2(String backup2) {
		getData().put("backup2", backup2);
		this.backup2 = backup2;
	}

	public String getBackup2() {
		return (String) getData().get("backup2");
	}

	private Integer creator;

	public void setCreator(Integer creator) {
		getData().put("creator", creator);
		this.creator = creator;
	}

	public Integer getCreator() {
		return (Integer) getData().get("creator");
	}

	private Timestamp createTime;

	public void setCreateTime(Timestamp createTime) {
		getData().put("create_time", createTime);
		this.createTime = createTime;
	}

	public Timestamp getCreateTime() {
		return (Timestamp) getData().get("create_time");
	}

	private Integer lastModby;

	public void setLastModby(Integer lastModby) {
		getData().put("last_modby", lastModby);
		this.lastModby = lastModby;
	}

	public Integer getLastModby() {
		return (Integer) getData().get("last_modby");
	}

	private Timestamp lastModtime;

	public void setLastModtime(Timestamp lastModtime) {
		getData().put("last_modtime", lastModtime);
		this.lastModtime = lastModtime;
	}

	public Timestamp getLastModtime() {
		return (Timestamp) getData().get("last_modtime");
	}

	private String openId;

	public void setOpenId(String openId) {
		getData().put("open_id", openId);
		this.openId = openId;
	}

	public String getOpenId() {
		return (String) getData().get("open_id");
	}

	private String userSex;

	public void setUserSex(String userSex) {
		getData().put("user_sex", userSex);
		this.userSex = userSex;
	}

	public String getUserSex() {
		return (String) getData().get("userSex");
	}

	private String web;

	public void setWeb(String web) {
		getData().put("web", web);
		this.web = web;
	}

	public String getWeb() {
		return (String) getData().get("web");
	}

}
