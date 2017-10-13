package com.liketry.interaction.opt.stockdetail.model;

import java.util.Arrays;
import java.sql.Timestamp;
import java.lang.String;
import com.taikang.udp.framework.common.datastructre.impl.BaseBO;


/**
  * StockDetailBO 
  */
public class StockDetailBO  extends BaseBO {
	
	private static final long serialVersionUID = 1L;
	
	public StockDetailBO(){
		init();
	}
	
	protected void init(){
		super.init();
		this.addList(Arrays.asList("detail_id","stock_id","order_id","start_time","end_time","created_time","created_by"));
	}
	
		
		
	private String detailId;
		public void setDetailId(String detailId){
		getData().put("detail_id",detailId);
		this.detailId=detailId;
	}
	
	public String getDetailId(){
		return (String)getData().get("detail_id");
	}	
		
		
	private String stockId;
		public void setStockId(String stockId){
		getData().put("stock_id",stockId);
		this.stockId=stockId;
	}
	
	public String getStockId(){
		return (String)getData().get("stock_id");
	}	
		
		
	private String orderId;
		public void setOrderId(String orderId){
		getData().put("order_id",orderId);
		this.orderId=orderId;
	}
	
	public String getOrderId(){
		return (String)getData().get("order_id");
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
	 }

