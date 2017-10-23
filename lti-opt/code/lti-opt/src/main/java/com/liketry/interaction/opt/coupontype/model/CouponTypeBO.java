package com.liketry.interaction.opt.coupontype.model;

import java.math.BigDecimal;
import java.util.Arrays;
import java.sql.Timestamp;
import java.lang.String;
import java.lang.Integer;
import com.taikang.udp.framework.common.datastructre.impl.BaseBO;


/**
  * CouponTypeBO 
  */
public class CouponTypeBO  extends BaseBO {
	
	private static final long serialVersionUID = 1L;
	
	public CouponTypeBO(){
		init();
	}
	
	protected void init(){
		super.init();
		this.addList(Arrays.asList("coupon_type_id","coupon_type_code","coupon_name","coupon_type","valid_type","publish_type","special_offer","discount","deduction","screen_ids","commodity_ids","benison_type_ids","created_time","created_by","modified_time","modified_by","version","delflag"));
	}
	
		
		
	private String couponTypeId;
		public void setCouponTypeId(String couponTypeId){
		getData().put("coupon_type_id",couponTypeId);
		this.couponTypeId=couponTypeId;
	}
	
	public String getCouponTypeId(){
		return (String)getData().get("coupon_type_id");
	}	
		
		
	private String couponTypeCode;
		public void setCouponTypeCode(String couponTypeCode){
		getData().put("coupon_type_code",couponTypeCode);
		this.couponTypeCode=couponTypeCode;
	}
	
	public String getCouponTypeCode(){
		return (String)getData().get("coupon_type_code");
	}	
		
		
	private String couponName;
		public void setCouponName(String couponName){
		getData().put("coupon_name",couponName);
		this.couponName=couponName;
	}
	
	public String getCouponName(){
		return (String)getData().get("coupon_name");
	}	
		
		
	private String couponType;
		public void setCouponType(String couponType){
		getData().put("coupon_type",couponType);
		this.couponType=couponType;
	}
	
	public String getCouponType(){
		return (String)getData().get("coupon_type");
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
		
		
	private BigDecimal specialOffer;
		public void setSpecialOffer(BigDecimal specialOffer){
		getData().put("special_offer",specialOffer);
		this.specialOffer=specialOffer;
	}
	
	public BigDecimal getSpecialOffer(){
		return (BigDecimal)getData().get("special_offer");
	}	
		
		
	private BigDecimal discount;
		public void setDiscount(BigDecimal discount){
		getData().put("discount",discount);
		this.discount=discount;
	}
	
	public BigDecimal getDiscount(){
		return (BigDecimal)getData().get("discount");
	}	
		
		
	private BigDecimal deduction;
		public void setDeduction(BigDecimal deduction){
		getData().put("deduction",deduction);
		this.deduction=deduction;
	}
	
	public BigDecimal getDeduction(){
		return (BigDecimal)getData().get("deduction");
	}	
		
		
	private String screenIds;
		public void setScreenIds(String screenIds){
		getData().put("screen_ids",screenIds);
		this.screenIds=screenIds;
	}
	
	public String getScreenIds(){
		return (String)getData().get("screen_ids");
	}	
		
		
	private String commodityIds;
		public void setCommodityIds(String commodityIds){
		getData().put("commodity_ids",commodityIds);
		this.commodityIds=commodityIds;
	}
	
	public String getCommodityIds(){
		return (String)getData().get("commodity_ids");
	}	
		
		
	private String benisonTypeIds;
		public void setBenisonTypeIds(String benisonTypeIds){
		getData().put("benison_type_ids",benisonTypeIds);
		this.benisonTypeIds=benisonTypeIds;
	}
	
	public String getBenisonTypeIds(){
		return (String)getData().get("benison_type_ids");
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

