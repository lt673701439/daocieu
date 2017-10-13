package com.liketry.interaction.opt.user.model;

import java.util.Arrays;
import java.sql.Timestamp;
import java.lang.String;
import com.taikang.udp.framework.common.datastructre.impl.BaseBO;


/**
  * BUserBO 
  */
public class BUserBO  extends BaseBO {
	
	private static final long serialVersionUID = 1L;
	
	public BUserBO(){
		init();
	}
	
	protected void init(){
		super.init();
		this.addList(Arrays.asList("user_id","user_nickname","user_pwd","user_type","user_icon","user_sex","user_birthday","user_province","user_city","user_address","user_mail","user_phone","open_id","created_time","delflag"));
	}
	
		
		
	private String userId;
		public void setUserId(String userId){
		getData().put("user_id",userId);
		this.userId=userId;
	}
	
	public String getUserId(){
		return (String)getData().get("user_id");
	}	
		
		
	private String userNickname;
		public void setUserNickname(String userNickname){
		getData().put("user_nickname",userNickname);
		this.userNickname=userNickname;
	}
	
	public String getUserNickname(){
		return (String)getData().get("user_nickname");
	}	
		
		
	private String userPwd;
		public void setUserPwd(String userPwd){
		getData().put("user_pwd",userPwd);
		this.userPwd=userPwd;
	}
	
	public String getUserPwd(){
		return (String)getData().get("user_pwd");
	}	
		
		
	private String userType;
		public void setUserType(String userType){
		getData().put("user_type",userType);
		this.userType=userType;
	}
	
	public String getUserType(){
		return (String)getData().get("user_type");
	}	
		
		
	private String userIcon;
		public void setUserIcon(String userIcon){
		getData().put("user_icon",userIcon);
		this.userIcon=userIcon;
	}
	
	public String getUserIcon(){
		return (String)getData().get("user_icon");
	}	
		
		
	private String userSex;
		public void setUserSex(String userSex){
		getData().put("user_sex",userSex);
		this.userSex=userSex;
	}
	
	public String getUserSex(){
		return (String)getData().get("user_sex");
	}	
		
		
	private String userBirthday;
		public void setUserBirthday(String userBirthday){
		getData().put("user_birthday",userBirthday);
		this.userBirthday=userBirthday;
	}
	
	public String getUserBirthday(){
		return (String)getData().get("user_birthday");
	}	
		
		
	private String userProvince;
		public void setUserProvince(String userProvince){
		getData().put("user_province",userProvince);
		this.userProvince=userProvince;
	}
	
	public String getUserProvince(){
		return (String)getData().get("user_province");
	}	
		
		
	private String userCity;
		public void setUserCity(String userCity){
		getData().put("user_city",userCity);
		this.userCity=userCity;
	}
	
	public String getUserCity(){
		return (String)getData().get("user_city");
	}	
		
		
	private String userAddress;
		public void setUserAddress(String userAddress){
		getData().put("user_address",userAddress);
		this.userAddress=userAddress;
	}
	
	public String getUserAddress(){
		return (String)getData().get("user_address");
	}	
		
		
	private String userMail;
		public void setUserMail(String userMail){
		getData().put("user_mail",userMail);
		this.userMail=userMail;
	}
	
	public String getUserMail(){
		return (String)getData().get("user_mail");
	}	
		
		
	private String userPhone;
		public void setUserPhone(String userPhone){
		getData().put("user_phone",userPhone);
		this.userPhone=userPhone;
	}
	
	public String getUserPhone(){
		return (String)getData().get("user_phone");
	}	
		
		
	private String openId;
		public void setOpenId(String openId){
		getData().put("open_id",openId);
		this.openId=openId;
	}
	
	public String getOpenId(){
		return (String)getData().get("open_id");
	}	
		
		
	private Timestamp createdTime;
		public void setCreatedTime(Timestamp createdTime){
		getData().put("created_time",createdTime);
		this.createdTime=createdTime;
	}
	
	public Timestamp getCreatedTime(){
		return (Timestamp)getData().get("created_time");
	}	
		
		
	private String delflag;
		public void setDelflag(String delflag){
		getData().put("delflag",delflag);
		this.delflag=delflag;
	}
	
	public String getDelflag(){
		return (String)getData().get("delflag");
	}	
	 }

