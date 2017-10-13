package com.liketry.interaction.opt.commoditysku.model;

import java.math.BigDecimal;
import java.util.Arrays;
import java.sql.Timestamp;
import java.lang.String;
import java.lang.Integer;
import com.taikang.udp.framework.common.datastructre.impl.BaseBO;


/**
  * CommoditySkuBO 
  */
public class CommoditySkuBO  extends BaseBO {
	
	private static final long serialVersionUID = 1L;
	
	public CommoditySkuBO(){
		init();
	}
	
	protected void init(){
		super.init();
		this.addList(Arrays.asList("sku_id","commodity_id","sku_code","sku_name","sku_description","sku_status","sku_price","template_id","type_id","benison_id","created_time","created_by","modified_time","modified_by","version","delflag"));
	}
	
		
		
	private String skuId;
		public void setSkuId(String skuId){
		getData().put("sku_id",skuId);
		this.skuId=skuId;
	}
	
	public String getSkuId(){
		return (String)getData().get("sku_id");
	}	
		
		
	private String commodityId;
		public void setCommodityId(String commodityId){
		getData().put("commodity_id",commodityId);
		this.commodityId=commodityId;
	}
	
	public String getCommodityId(){
		return (String)getData().get("commodity_id");
	}	
		
		
	private String skuCode;
		public void setSkuCode(String skuCode){
		getData().put("sku_code",skuCode);
		this.skuCode=skuCode;
	}
	
	public String getSkuCode(){
		return (String)getData().get("sku_code");
	}	
		
		
	private String skuName;
		public void setSkuName(String skuName){
		getData().put("sku_name",skuName);
		this.skuName=skuName;
	}
	
	public String getSkuName(){
		return (String)getData().get("sku_name");
	}	
		
		
	private String skuDescription;
		public void setSkuDescription(String skuDescription){
		getData().put("sku_description",skuDescription);
		this.skuDescription=skuDescription;
	}
	
	public String getSkuDescription(){
		return (String)getData().get("sku_description");
	}	
		
		
	private String skuStatus;
		public void setSkuStatus(String skuStatus){
		getData().put("sku_status",skuStatus);
		this.skuStatus=skuStatus;
	}
	
	public String getSkuStatus(){
		return (String)getData().get("sku_status");
	}	
		
		
	private BigDecimal skuPrice;
		public void setSkuPrice(BigDecimal skuPrice){
		getData().put("sku_price",skuPrice);
		this.skuPrice=skuPrice;
	}
	
	public BigDecimal getSkuPrice(){
		return (BigDecimal)getData().get("sku_price");
	}	
		
		
	private String templateId;
		public void setTemplateId(String templateId){
		getData().put("template_id",templateId);
		this.templateId=templateId;
	}
	
	public String getTemplateId(){
		return (String)getData().get("template_id");
	}	
		
		
	private String typeId;
		public void setTypeId(String typeId){
		getData().put("type_id",typeId);
		this.typeId=typeId;
	}
	
	public String getTypeId(){
		return (String)getData().get("type_id");
	}	
		
		
	private String benisonId;
		public void setBenisonId(String benisonId){
		getData().put("benison_id",benisonId);
		this.benisonId=benisonId;
	}
	
	public String getBenisonId(){
		return (String)getData().get("benison_id");
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

