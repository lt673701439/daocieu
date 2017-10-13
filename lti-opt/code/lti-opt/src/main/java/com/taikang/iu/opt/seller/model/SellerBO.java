package com.taikang.iu.opt.seller.model;

import java.sql.Timestamp;
import java.lang.Integer;
import java.lang.String;
import java.util.Arrays;
import com.taikang.udp.framework.common.datastructre.impl.BaseBO;


/**
  * SellerBO 
  */
public class SellerBO  extends BaseBO {
	
	private static final long serialVersionUID = 1L;
	
	public SellerBO(){
		init();
	}
	
	protected void init(){
		super.init();
		this.addList(Arrays.asList("seller_id","user_id","seller_name","seller_code","seller_linkman","seller_tel","seller_mobile","seller_address","seller_type","seller_state","site","bank_name","bank_account","account_holder","login_name","login_init_pwd","created_time","created_by","modified_time","modified_by","version","delflag"));
	}
	
		
		
	private String sellerId;
		public void setSellerId(String sellerId){
		getData().put("seller_id",sellerId);
		this.sellerId=sellerId;
	}
	
	public String getSellerId(){
		return (String)getData().get("seller_id");
	}	
		
		
	private Integer userId;
		public void setUserId(Integer userId){
		getData().put("user_id",userId);
		this.userId=userId;
	}
	
	public Integer getUserId(){
		return (Integer)getData().get("user_id");
	}	
		
		
	private String sellerName;
		public void setSellerName(String sellerName){
		getData().put("seller_name",sellerName);
		this.sellerName=sellerName;
	}
	
	public String getSellerName(){
		return (String)getData().get("seller_name");
	}	
		
		
	private String sellerCode;
		public void setSellerCode(String sellerCode){
		getData().put("seller_code",sellerCode);
		this.sellerCode=sellerCode;
	}
	
	public String getSellerCode(){
		return (String)getData().get("seller_code");
	}	
		
		
	private String sellerLinkman;
		public void setSellerLinkman(String sellerLinkman){
		getData().put("seller_linkman",sellerLinkman);
		this.sellerLinkman=sellerLinkman;
	}
	
	public String getSellerLinkman(){
		return (String)getData().get("seller_linkman");
	}	
		
		
	private String sellerTel;
		public void setSellerTel(String sellerTel){
		getData().put("seller_tel",sellerTel);
		this.sellerTel=sellerTel;
	}
	
	public String getSellerTel(){
		return (String)getData().get("seller_tel");
	}	
		
		
	private String sellerMobile;
		public void setSellerMobile(String sellerMobile){
		getData().put("seller_mobile",sellerMobile);
		this.sellerMobile=sellerMobile;
	}
	
	public String getSellerMobile(){
		return (String)getData().get("seller_mobile");
	}	
		
		
	private String sellerAddress;
		public void setSellerAddress(String sellerAddress){
		getData().put("seller_address",sellerAddress);
		this.sellerAddress=sellerAddress;
	}
	
	public String getSellerAddress(){
		return (String)getData().get("seller_address");
	}	
		
		
	private String sellerType;
		public void setSellerType(String sellerType){
		getData().put("seller_type",sellerType);
		this.sellerType=sellerType;
	}
	
	public String getSellerType(){
		return (String)getData().get("seller_type");
	}	
		
		
	private String sellerState;
		public void setSellerState(String sellerState){
		getData().put("seller_state",sellerState);
		this.sellerState=sellerState;
	}
	
	public String getSellerState(){
		return (String)getData().get("seller_state");
	}	
		
		
	private String site;
		public void setSite(String site){
		getData().put("site",site);
		this.site=site;
	}
	
	public String getSite(){
		return (String)getData().get("site");
	}	
		
		
	private String bankName;
		public void setBankName(String bankName){
		getData().put("bank_name",bankName);
		this.bankName=bankName;
	}
	
	public String getBankName(){
		return (String)getData().get("bank_name");
	}	
		
		
	private String bankAccount;
		public void setBankAccount(String bankAccount){
		getData().put("bank_account",bankAccount);
		this.bankAccount=bankAccount;
	}
	
	public String getBankAccount(){
		return (String)getData().get("bank_account");
	}	
		
		
	private String accountHolder;
		public void setAccountHolder(String accountHolder){
		getData().put("account_holder",accountHolder);
		this.accountHolder=accountHolder;
	}
	
	public String getAccountHolder(){
		return (String)getData().get("account_holder");
	}	
		
		
	private String loginName;
		public void setLoginName(String loginName){
		getData().put("login_name",loginName);
		this.loginName=loginName;
	}
	
	public String getLoginName(){
		return (String)getData().get("login_name");
	}	
		
		
	private String loginInitPwd;
		public void setLoginInitPwd(String loginInitPwd){
		getData().put("login_init_pwd",loginInitPwd);
		this.loginInitPwd=loginInitPwd;
	}
	
	public String getLoginInitPwd(){
		return (String)getData().get("login_init_pwd");
	}	
		
		
	private Timestamp createdTime;
		public void setCreatedTime(Timestamp createdTime){
		getData().put("created_time",createdTime);
		this.createdTime=createdTime;
	}
	
	public Timestamp getCreatedTime(){
		return (Timestamp)getData().get("created_time");
	}	
		
		
	private String createdBy;
		public void setCreatedBy(String createdBy){
		getData().put("created_by",createdBy);
		this.createdBy=createdBy;
	}
	
	public String getCreatedBy(){
		return (String)getData().get("created_by");
	}	
		
		
	private Timestamp modifiedTime;
		public void setModifiedTime(Timestamp modifiedTime){
		getData().put("modified_time",modifiedTime);
		this.modifiedTime=modifiedTime;
	}
	
	public Timestamp getModifiedTime(){
		return (Timestamp)getData().get("modified_time");
	}	
		
		
	private String modifiedBy;
		public void setModifiedBy(String modifiedBy){
		getData().put("modified_by",modifiedBy);
		this.modifiedBy=modifiedBy;
	}
	
	public String getModifiedBy(){
		return (String)getData().get("modified_by");
	}	
		
		
	private Integer version;
		public void setVersion(Integer version){
		getData().put("version",version);
		this.version=version;
	}
	
	public Integer getVersion(){
		return (Integer)getData().get("version");
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

