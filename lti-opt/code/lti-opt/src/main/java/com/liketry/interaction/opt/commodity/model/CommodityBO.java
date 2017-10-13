package com.liketry.interaction.opt.commodity.model;

import java.math.BigDecimal;
import java.util.Arrays;
import java.sql.Timestamp;
import java.lang.String;
import java.lang.Integer;
import com.taikang.udp.framework.common.datastructre.impl.BaseBO;


/**
  * CommodityBO 
  */
public class CommodityBO  extends BaseBO {
	
	private static final long serialVersionUID = 1L;
	
	public CommodityBO(){
		init();
	}
	
	protected void init(){
		super.init();
		this.addList(Arrays.asList("commodity_id","commodity_code","commodity_name","commodity_description","commodity_type","commodity_status","commodity_price","commodity_img","screen_id","start_date","end_date","time_frame","start_time","end_time","shelf_time","plan_number","single_time","play_times","created_time","created_by","modified_time","modified_by","version","delflag","screen_name"));
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
		
		
	private String commodityType;
		public void setCommodityType(String commodityType){
		getData().put("commodity_type",commodityType);
		this.commodityType=commodityType;
	}
	
	public String getCommodityType(){
		return (String)getData().get("commodity_type");
	}	
		
		
	private String commodityStatus;
		public void setCommodityStatus(String commodityStatus){
		getData().put("commodity_status",commodityStatus);
		this.commodityStatus=commodityStatus;
	}
	
	public String getCommodityStatus(){
		return (String)getData().get("commodity_status");
	}	
		
		
	private BigDecimal commodityPrice;
		public void setCommodityPrice(BigDecimal commodityPrice){
		getData().put("commodity_price",commodityPrice);
		this.commodityPrice=commodityPrice;
	}
	
	public BigDecimal getCommodityPrice(){
		return (BigDecimal)getData().get("commodity_price");
	}	
		
		
	private String commodityImg;
		public void setCommodityImg(String commodityImg){
		getData().put("commodity_img",commodityImg);
		this.commodityImg=commodityImg;
	}
	
	public String getCommodityImg(){
		return (String)getData().get("commodity_img");
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
		
		
	private String timeFrame;
		public void setTimeFrame(String timeFrame){
		getData().put("time_frame",timeFrame);
		this.timeFrame=timeFrame;
	}
	
	public String getTimeFrame(){
		return (String)getData().get("time_frame");
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
		
		
	private String shelfTime;
		public void setShelfTime(String shelfTime){
		getData().put("shelf_time",shelfTime);
		this.shelfTime=shelfTime;
	}
	
	public String getShelfTime(){
		return (String)getData().get("shelf_time");
	}	
		
		
	private Integer planNumber;
		public void setPlanNumber(Integer planNumber){
		getData().put("plan_number",planNumber);
		this.planNumber=planNumber;
	}
	
	public Integer getPlanNumber(){
		return (Integer)getData().get("plan_number");
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

	private String screenName;
	public void setScreenName(String screenName){
		getData().put("screen_name",screenName);
		this.screenName=screenName;
	}
	
	public String getScreenName(){
		return (String)getData().get("screen_name");
	}
}

