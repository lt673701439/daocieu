package com.liketry.interaction.opt.promotiondetail.model;

import java.util.Arrays;
import java.sql.Timestamp;
import java.lang.String;
import java.math.BigDecimal;
import java.lang.Integer;
import com.taikang.udp.framework.common.datastructre.impl.BaseBO;


/**
  * PromotionDetailBO 
  */
public class PromotionDetailBO  extends BaseBO {
	
	private static final long serialVersionUID = 1L;
	
	public PromotionDetailBO(){
		init();
	}
	
	protected void init(){
		super.init();
		this.addList(Arrays.asList("detail_id","promotion_id","commodity_id","commodity_num","discount_ratio","created_time","created_by","modified_time","modified_by","version","delflag",
				"commodity_name","commodity_price","commodity_img","start_time","end_time","screen_name"));
	}
	
		
		
	private String detailId;
		public void setDetailId(String detailId){
		getData().put("detail_id",detailId);
		this.detailId=detailId;
	}
	
	public String getDetailId(){
		return (String)getData().get("detail_id");
	}	
		
		
	private String promotionId;
		public void setPromotionId(String promotionId){
		getData().put("promotion_id",promotionId);
		this.promotionId=promotionId;
	}
	
	public String getPromotionId(){
		return (String)getData().get("promotion_id");
	}	
		
		
	private String commodityId;
		public void setCommodityId(String commodityId){
		getData().put("commodity_id",commodityId);
		this.commodityId=commodityId;
	}
	
	public String getCommodityId(){
		return (String)getData().get("commodity_id");
	}
	
	private Integer commodityNum;
	public void setCommodityNum(Integer commodityNum){
		getData().put("commodity_num",commodityNum);
		this.commodityNum=commodityNum;
	}
	
	public Integer getCommodityNum(){
		return (Integer)getData().get("commodity_num");
	}	
		
	private Float discountRatio;
		public void setDiscountRatio(Float discountRatio){
		getData().put("discount_ratio",discountRatio);
		this.discountRatio=discountRatio;
	}
	
	public Float getDiscountRatio(){
		return (Float)getData().get("discount_ratio");
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
	
	private String screenName;
	public void setScreenName(String screenName){
		getData().put("screen_name",screenName);
		this.screenName=screenName;
	}
	
	public String getScreenName(){
		return (String)getData().get("screen_name");
	}
}

