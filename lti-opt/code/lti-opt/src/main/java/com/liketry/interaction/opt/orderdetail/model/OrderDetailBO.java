package com.liketry.interaction.opt.orderdetail.model;

import java.math.BigDecimal;
import java.util.Arrays;
import java.sql.Timestamp;
import java.lang.String;
import java.lang.Integer;
import com.taikang.udp.framework.common.datastructre.impl.BaseBO;


/**
  * OrderDetailBO 
  */
public class OrderDetailBO  extends BaseBO {
	
	private static final long serialVersionUID = 1L;
	
	public OrderDetailBO(){
		init();
	}
	
	protected void init(){
		super.init();
		this.addList(Arrays.asList("detail_id","order_id","commodity_id","commodity_code","commodity_name","commodity_description","commodity_price","play_date","time_start","time_end","single_time","play_times","sku_id","sku_code","sku_name","sku_description","sku_price","export_by","export_id","export_time","created_time","created_by","modified_time","modified_by","version","delflag"));
	}
	
		
		
	private String detailId;
		public void setDetailId(String detailId){
		getData().put("detail_id",detailId);
		this.detailId=detailId;
	}
	
	public String getDetailId(){
		return (String)getData().get("detail_id");
	}	
		
		
	private String orderId;
		public void setOrderId(String orderId){
		getData().put("order_id",orderId);
		this.orderId=orderId;
	}
	
	public String getOrderId(){
		return (String)getData().get("order_id");
	}	
		
		
	private String commodityId;
		public void setCommodityId(String commodityId){
		getData().put("commodity_id",commodityId);
		this.commodityId=commodityId;
	}
	
	public String getCommodityId(){
		return (String)getData().get("commodity_id");
	}	
		
		
	private String commodityCode;
		public void setCommodityCode(String commodityCode){
		getData().put("commodity_code",commodityCode);
		this.commodityCode=commodityCode;
	}
	
	public String getCommodityCode(){
		return (String)getData().get("commodity_code");
	}	
		
		
	private String commodityName;
		public void setCommodityName(String commodityName){
		getData().put("commodity_name",commodityName);
		this.commodityName=commodityName;
	}
	
	public String getCommodityName(){
		return (String)getData().get("commodity_name");
	}	
		
		
	private String commodityDescription;
		public void setCommodityDescription(String commodityDescription){
		getData().put("commodity_description",commodityDescription);
		this.commodityDescription=commodityDescription;
	}
	
	public String getCommodityDescription(){
		return (String)getData().get("commodity_description");
	}	
		
		
	private BigDecimal commodityPrice;
		public void setCommodityPrice(BigDecimal commodityPrice){
		getData().put("commodity_price",commodityPrice);
		this.commodityPrice=commodityPrice;
	}
	
	public BigDecimal getCommodityPrice(){
		return (BigDecimal)getData().get("commodity_price");
	}	
		
		
	private String playDate;
		public void setPlayDate(String playDate){
		getData().put("play_date",playDate);
		this.playDate=playDate;
	}
	
	public String getPlayDate(){
		return (String)getData().get("play_date");
	}	
		
		
	private String timeStart;
		public void setTimeStart(String timeStart){
		getData().put("time_start",timeStart);
		this.timeStart=timeStart;
	}
	
	public String getTimeStart(){
		return (String)getData().get("time_start");
	}	
		
		
	private String timeEnd;
		public void setTimeEnd(String timeEnd){
		getData().put("time_end",timeEnd);
		this.timeEnd=timeEnd;
	}
	
	public String getTimeEnd(){
		return (String)getData().get("time_end");
	}	
		
		
	private Integer singleTime;
		public void setSingleTime(Integer singleTime){
		getData().put("single_time",singleTime);
		this.singleTime=singleTime;
	}
	
	public Integer getSingleTime(){
		return (Integer)getData().get("single_time");
	}	
		
		
	private Integer playTimes;
		public void setPlayTimes(Integer playTimes){
		getData().put("play_times",playTimes);
		this.playTimes=playTimes;
	}
	
	public Integer getPlayTimes(){
		return (Integer)getData().get("play_times");
	}	
		
		
	private String skuId;
		public void setSkuId(String skuId){
		getData().put("sku_id",skuId);
		this.skuId=skuId;
	}
	
	public String getSkuId(){
		return (String)getData().get("sku_id");
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
		
		
	private BigDecimal skuPrice;
		public void setSkuPrice(BigDecimal skuPrice){
		getData().put("sku_price",skuPrice);
		this.skuPrice=skuPrice;
	}
	
	public BigDecimal getSkuPrice(){
		return (BigDecimal)getData().get("sku_price");
	}	
		
		
	private String exportBy;
		public void setExportBy(String exportBy){
		getData().put("export_by",exportBy);
		this.exportBy=exportBy;
	}
	
	public String getExportBy(){
		return (String)getData().get("export_by");
	}	
		
		
	private String exportId;
		public void setExportId(String exportId){
		getData().put("export_id",exportId);
		this.exportId=exportId;
	}
	
	public String getExportId(){
		return (String)getData().get("export_id");
	}	
		
		
	private String exportTime;
		public void setExportTime(String exportTime){
		getData().put("export_time",exportTime);
		this.exportTime=exportTime;
	}
	
	public String getExportTime(){
		return (String)getData().get("export_time");
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

