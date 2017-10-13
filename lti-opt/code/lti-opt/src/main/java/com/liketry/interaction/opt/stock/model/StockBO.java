package com.liketry.interaction.opt.stock.model;

import java.util.Arrays;
import java.sql.Timestamp;
import java.lang.String;
import java.lang.Integer;
import com.taikang.udp.framework.common.datastructre.impl.BaseBO;


/**
  * StockBO 
  */
public class StockBO  extends BaseBO {
	
	private static final long serialVersionUID = 1L;
	
	public StockBO(){
		init();
	}
	
	protected void init(){
		super.init();
		this.addList(Arrays.asList("stock_id","commodity_id","stock_status","stock_date","start_time","end_time","sales","stock","schedule_status","created_time","created_by","modified_time","modified_by","version","delflag","commodity_name","commodity_status"));
	}
	
		
		
	private String stockId;
		public void setStockId(String stockId){
		getData().put("stock_id",stockId);
		this.stockId=stockId;
	}
	
	public String getStockId(){
		return (String)getData().get("stock_id");
	}	
		
		
	private String commodityId;
		public void setCommodityId(String commodityId){
		getData().put("commodity_id",commodityId);
		this.commodityId=commodityId;
	}
	
	public String getCommodityId(){
		return (String)getData().get("commodity_id");
	}	
		
		
	private String stockStatus;
		public void setStockStatus(String stockStatus){
		getData().put("stock_status",stockStatus);
		this.stockStatus=stockStatus;
	}
	
	public String getStockStatus(){
		return (String)getData().get("stock_status");
	}	
		
		
	private String stockDate;
		public void setStockDate(String stockDate){
		getData().put("stock_date",stockDate);
		this.stockDate=stockDate;
	}
	
	public String getStockDate(){
		return (String)getData().get("stock_date");
	}	
		
		
	private String startTime;
		public void setStartTime(String startTime){
		getData().put("start_time",startTime);
		this.startTime=startTime;
	}
	
	public String getStartTime(){
		return (String)getData().get("start_time");
	}	
		
		
	private String endTime;
		public void setEndTime(String endTime){
		getData().put("end_time",endTime);
		this.endTime=endTime;
	}
	
	public String getEndTime(){
		return (String)getData().get("end_time");
	}	
		
		
	private Integer sales;
		public void setSales(Integer sales){
		getData().put("sales",sales);
		this.sales=sales;
	}
	
	public Integer getSales(){
		return (Integer)getData().get("sales");
	}	
		
		
	private Integer stock;
		public void setStock(Integer stock){
		getData().put("stock",stock);
		this.stock=stock;
	}
	
	public Integer getStock(){
		return (Integer)getData().get("stock");
	}	
		
		
	private String scheduleStatus;
		public void setScheduleStatus(String scheduleStatus){
		getData().put("schedule_status",scheduleStatus);
		this.scheduleStatus=scheduleStatus;
	}
	
	public String getScheduleStatus(){
		return (String)getData().get("schedule_status");
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
	
	private String commodityName;

	public String getCommodityName() {
		return (String)getData().get("commodity_name");
	}

	public void setCommodityName(String commodityName) {
		getData().put("commodity_name",commodityName);
		this.commodityName=commodityName;
	}
	
	private String commodityStatus;

	public String getCommodityStatus() {
		return (String)getData().get("commodity_status");
	}

	public void setCommodityStatus(String commodityStatus) {
		getData().put("commodity_status",commodityStatus);
		this.commodityStatus=commodityStatus;
	}
	
}
