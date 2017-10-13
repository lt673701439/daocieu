package com.liketry.interaction.opt.promotion.model;

import java.util.Arrays;
import java.sql.Timestamp;
import java.lang.String;
import java.lang.Integer;
import com.taikang.udp.framework.common.datastructre.impl.BaseBO;


/**
  * PromotionBO 
  */
public class PromotionBO  extends BaseBO {
	
	private static final long serialVersionUID = 1L;
	
	public PromotionBO(){
		init();
	}
	
	protected void init(){
		super.init();
		this.addList(Arrays.asList("promotion_id","promotion_code","promotion_name","promotion_description","sort_num","promotion_type","promotion_status","promotion_img","back_up","add_time","remove_time","start_time","end_time","is_together","created_time","created_by","modified_time","modified_by","version","delflag"));
	}
	
		
		
	private String promotionId;
		public void setPromotionId(String promotionId){
		getData().put("promotion_id",promotionId);
		this.promotionId=promotionId;
	}
	
	public String getPromotionId(){
		return (String)getData().get("promotion_id");
	}	
		
		
	private String promotionCode;
		public void setPromotionCode(String promotionCode){
		getData().put("promotion_code",promotionCode);
		this.promotionCode=promotionCode;
	}
	
	public String getPromotionCode(){
		return (String)getData().get("promotion_code");
	}	
		
		
	private String promotionName;
		public void setPromotionName(String promotionName){
		getData().put("promotion_name",promotionName);
		this.promotionName=promotionName;
	}
	
	public String getPromotionName(){
		return (String)getData().get("promotion_name");
	}	
		
		
	private String promotionDescription;
		public void setPromotionDescription(String promotionDescription){
		getData().put("promotion_description",promotionDescription);
		this.promotionDescription=promotionDescription;
	}
	
	public String getPromotionDescription(){
		return (String)getData().get("promotion_description");
	}	
		
		
	private Integer sortNum;
		public void setSortNum(Integer sortNum){
		getData().put("sort_num",sortNum);
		this.sortNum=sortNum;
	}
	
	public Integer getSortNum(){
		return (Integer)getData().get("sort_num");
	}	
		
		
	private String promotionType;
		public void setPromotionType(String promotionType){
		getData().put("promotion_type",promotionType);
		this.promotionType=promotionType;
	}
	
	public String getPromotionType(){
		return (String)getData().get("promotion_type");
	}	
		
		
	private String promotionStatus;
		public void setPromotionStatus(String promotionStatus){
		getData().put("promotion_status",promotionStatus);
		this.promotionStatus=promotionStatus;
	}
	
	public String getPromotionStatus(){
		return (String)getData().get("promotion_status");
	}	
		
		
	private String promotionImg;
		public void setPromotionImg(String promotionImg){
		getData().put("promotion_img",promotionImg);
		this.promotionImg=promotionImg;
	}
	
	public String getPromotionImg(){
		return (String)getData().get("promotion_img");
	}	
		
		
	private String backUp;
		public void setBackUp(String backUp){
		getData().put("back_up",backUp);
		this.backUp=backUp;
	}
	
	public String getBackUp(){
		return (String)getData().get("back_up");
	}	
		
		
	private String addTime;
		public void setAddTime(String addTime){
		getData().put("add_time",addTime);
		this.addTime=addTime;
	}
	
	public String getAddTime(){
		return (String)getData().get("add_time");
	}	
		
		
	private String removeTime;
		public void setRemoveTime(String removeTime){
		getData().put("remove_time",removeTime);
		this.removeTime=removeTime;
	}
	
	public String getRemoveTime(){
		return (String)getData().get("remove_time");
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
		
		
	private String isTogether;
		public void setIsTogether(String isTogether){
		getData().put("is_together",isTogether);
		this.isTogether=isTogether;
	}
	
	public String getIsTogether(){
		return (String)getData().get("is_together");
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

