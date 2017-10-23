package com.liketry.interaction.opt.coupon.model;

import java.util.Arrays;
import java.sql.Timestamp;
import java.lang.String;
import java.lang.Integer;
import java.sql.Date;
import com.taikang.udp.framework.common.datastructre.impl.BaseBO;


/**
  * CouponBO 
  */
public class CouponBO  extends BaseBO {
	
	private static final long serialVersionUID = 1L;
	
	public CouponBO(){
		init();
	}
	
	protected void init(){
		super.init();
		this.addList(Arrays.asList("coupon_id","coupon_type_id","coupon_code","coupon_name","coupon_num","valid_type","publish_type","use_type","expire","publish_target_type","personal_name","id_card","merchant_name","business_licence","valid_date","publish_by","publish_date","order_code","use_time","created_time","created_by","modified_time","modified_by","version","delflag","coupon_type"));
	}
	
		
		
	private String couponId;
		public void setCouponId(String couponId){
		getData().put("coupon_id",couponId);
		this.couponId=couponId;
	}
	
	public String getCouponId(){
		return (String)getData().get("coupon_id");
	}	
		
		
	private String couponTypeId;
		public void setCouponTypeId(String couponTypeId){
		getData().put("coupon_type_id",couponTypeId);
		this.couponTypeId=couponTypeId;
	}
	
	public String getCouponTypeId(){
		return (String)getData().get("coupon_type_id");
	}	
		
		
	private String couponCode;
		public void setCouponCode(String couponCode){
		getData().put("coupon_code",couponCode);
		this.couponCode=couponCode;
	}
	
	public String getCouponCode(){
		return (String)getData().get("coupon_code");
	}	
		
		
	private String couponName;
		public void setCouponName(String couponName){
		getData().put("coupon_name",couponName);
		this.couponName=couponName;
	}
	
	public String getCouponName(){
		return (String)getData().get("coupon_name");
	}	
		
		
	private Integer couponNum;
		public void setCouponNum(Integer couponNum){
		getData().put("coupon_num",couponNum);
		this.couponNum=couponNum;
	}
	
	public Integer getCouponNum(){
		return (Integer)getData().get("coupon_num");
	}	
		
		
	private String validType;
		public void setValidType(String validType){
		getData().put("valid_type",validType);
		this.validType=validType;
	}
	
	public String getValidType(){
		return (String)getData().get("valid_type");
	}	
		
		
	private String publishType;
		public void setPublishType(String publishType){
		getData().put("publish_type",publishType);
		this.publishType=publishType;
	}
	
	public String getPublishType(){
		return (String)getData().get("publish_type");
	}	
		
		
	private String useType;
		public void setUseType(String useType){
		getData().put("use_type",useType);
		this.useType=useType;
	}
	
	public String getUseType(){
		return (String)getData().get("use_type");
	}	
	
	private String expire;
	public void setExpire(String expire){
		getData().put("expire",expire);
		this.expire=expire;
	}
	
	public String getExpire(){
		return (String)getData().get("expire");
	}	
		
		
	private String publishTargetType;
		public void setPublishTargetType(String publishTargetType){
		getData().put("publish_target_type",publishTargetType);
		this.publishTargetType=publishTargetType;
	}
	
	public String getPublishTargetType(){
		return (String)getData().get("publish_target_type");
	}	
		
		
	private String personalName;
		public void setPersonalName(String personalName){
		getData().put("personal_name",personalName);
		this.personalName=personalName;
	}
	
	public String getPersonalName(){
		return (String)getData().get("personal_name");
	}	
		
		
	private String idCard;
		public void setIdCard(String idCard){
		getData().put("id_card",idCard);
		this.idCard=idCard;
	}
	
	public String getIdCard(){
		return (String)getData().get("id_card");
	}	
		
		
	private String merchantName;
		public void setMerchantName(String merchantName){
		getData().put("merchant_name",merchantName);
		this.merchantName=merchantName;
	}
	
	public String getMerchantName(){
		return (String)getData().get("merchant_name");
	}	
		
		
	private String businessLicence;
		public void setBusinessLicence(String businessLicence){
		getData().put("business_licence",businessLicence);
		this.businessLicence=businessLicence;
	}
	
	public String getBusinessLicence(){
		return (String)getData().get("business_licence");
	}	
		
		
	private Date validDate;
		public void setValidDate(Date validDate){
		getData().put("valid_date",validDate);
		this.validDate=validDate;
	}
	
	public Date getValidDate(){
		return (Date)getData().get("valid_date");
	}	
		
		
	private String publishBy;
		public void setPublishBy(String publishBy){
		getData().put("publish_by",publishBy);
		this.publishBy=publishBy;
	}
	
	public String getPublishBy(){
		return (String)getData().get("publish_by");
	}	
		
		
	private Timestamp publishDate;
		public void setPublishDate(Timestamp publishDate){
		getData().put("publish_date",publishDate);
		this.publishDate=publishDate;
	}
	
	public Timestamp getPublishDate(){
		return (Timestamp)getData().get("publish_date");
	}	
		
		
	private String orderCode;
		public void setOrderCode(String orderCode){
		getData().put("order_code",orderCode);
		this.orderCode=orderCode;
	}
	
	public String getOrderCode(){
		return (String)getData().get("order_code");
	}	
		
		
	private Timestamp useTime;
		public void setUseTime(Timestamp useTime){
		getData().put("use_time",useTime);
		this.useTime=useTime;
	}
	
	public Timestamp getUseTime(){
		return (Timestamp)getData().get("use_time");
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
	
	private String couponType;
	public void setCouponType(String couponType){
		getData().put("coupon_type",couponType);
		this.couponType=couponType;
	}
	
	public String getCouponType(){
		return (String)getData().get("coupon_type");
	}
	 }

