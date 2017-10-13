package com.liketry.interaction.opt.confcustom.model;

import java.math.BigDecimal;
import java.util.Arrays;
import java.sql.Timestamp;
import java.lang.String;
import java.lang.Integer;
import com.taikang.udp.framework.common.datastructre.impl.BaseBO;


/**
  * ConfCustomBO 
  */
public class ConfCustomBO  extends BaseBO {
	
	private static final long serialVersionUID = 1L;
	
	public ConfCustomBO(){
		init();
	}
	
	protected void init(){
		super.init();
		this.addList(Arrays.asList("custom_id","custom_type","custom_price","created_time","created_by","modified_time","modified_by","version","delflag"));
	}
	
		
		
	private String customId;
		public void setCustomId(String customId){
		getData().put("custom_id",customId);
		this.customId=customId;
	}
	
	public String getCustomId(){
		return (String)getData().get("custom_id");
	}	
		
		
	private String customType;
		public void setCustomType(String customType){
		getData().put("custom_type",customType);
		this.customType=customType;
	}
	
	public String getCustomType(){
		return (String)getData().get("custom_type");
	}	
		
		
	private BigDecimal customPrice;
		public void setCustomPrice(BigDecimal customPrice){
		getData().put("custom_price",customPrice);
		this.customPrice=customPrice;
	}
	
	public BigDecimal getCustomPrice(){
		return (BigDecimal)getData().get("custom_price");
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

