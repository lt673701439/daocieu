
package com.liketry.interaction.opt.screenstock.model;

import java.util.Arrays;
import java.sql.Timestamp;
import java.lang.String;
import java.lang.Integer;
import com.taikang.udp.framework.common.datastructre.impl.BaseBO;


/**
  * ScreenStockBO 
  */
public class ScreenStockBO  extends BaseBO {
	
	private static final long serialVersionUID = 1L;
	
	public ScreenStockBO(){
		init();
	}
	
	protected void init(){
		super.init();
		this.addList(Arrays.asList("stock_id","screen_id","start_date","end_date","start_time","end_time","created_time","created_by","modified_time","modified_by","version","delflag","screen_name"));
	}
	
		
		
	private String stockId;
		public void setStockId(String stockId){
		getData().put("stock_id",stockId);
		this.stockId=stockId;
	}
	
	public String getStockId(){
		return (String)getData().get("stock_id");
	}	
		
		
	private String screenId;
		public void setScreenId(String screenId){
		getData().put("screen_id",screenId);
		this.screenId=screenId;
	}
	
	public String getScreenId(){
		return (String)getData().get("screen_id");
	}	
		
		
	private String startDate;
		public void setStartDate(String startDate){
		getData().put("start_date",startDate);
		this.startDate=startDate;
	}
	
	public String getStartDate(){
		return (String)getData().get("start_date");
	}	
		
		
	private String endDate;
		public void setEndDate(String endDate){
		getData().put("end_date",endDate);
		this.endDate=endDate;
	}
	
	public String getEndDate(){
		return (String)getData().get("end_date");
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
	
	private String screenName;//屏幕名称
	public void setScreenName(String screenName){
		getData().put("screen_name",screenName);
		this.screenName=screenName;
	}
	
	public String getScreenName(){
		return (String)getData().get("screen_name");
	}
	 }

