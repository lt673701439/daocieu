package com.liketry.interaction.opt.spot.model;

import java.util.Arrays;
import java.lang.String;
import com.taikang.udp.framework.common.datastructre.impl.BaseBO;


/**
  * SpotBO 
  */
public class SpotBO  extends BaseBO {
	
	private static final long serialVersionUID = 1L;
	
	public SpotBO(){
		init();
	}
	
	protected void init(){
		super.init();
		this.addList(Arrays.asList("spot_id","spot_code","spot_name","spot_status","spot_province","spot_city","spot_address","spot_description"));
	}
	
		
		
	private String spotId;
		public void setSpotId(String spotId){
		getData().put("spot_id",spotId);
		this.spotId=spotId;
	}
	
	public String getSpotId(){
		return (String)getData().get("spot_id");
	}	
		
		
	private String spotCode;
		public void setSpotCode(String spotCode){
		getData().put("spot_code",spotCode);
		this.spotCode=spotCode;
	}
	
	public String getSpotCode(){
		return (String)getData().get("spot_code");
	}	
		
		
	private String spotName;
		public void setSpotName(String spotName){
		getData().put("spot_name",spotName);
		this.spotName=spotName;
	}
	
	public String getSpotName(){
		return (String)getData().get("spot_name");
	}	
		
		
	private String spotStatus;
		public void setSpotStatus(String spotStatus){
		getData().put("spot_status",spotStatus);
		this.spotStatus=spotStatus;
	}
	
	public String getSpotStatus(){
		return (String)getData().get("spot_status");
	}	
		
		
	private String spotProvince;
		public void setSpotProvince(String spotProvince){
		getData().put("spot_province",spotProvince);
		this.spotProvince=spotProvince;
	}
	
	public String getSpotProvince(){
		return (String)getData().get("spot_province");
	}	
		
		
	private String spotCity;
		public void setSpotCity(String spotCity){
		getData().put("spot_city",spotCity);
		this.spotCity=spotCity;
	}
	
	public String getSpotCity(){
		return (String)getData().get("spot_city");
	}	
		
		
	private String spotAddress;
		public void setSpotAddress(String spotAddress){
		getData().put("spot_address",spotAddress);
		this.spotAddress=spotAddress;
	}
	
	public String getSpotAddress(){
		return (String)getData().get("spot_address");
	}	
		
		
	private String spotDescription;
		public void setSpotDescription(String spotDescription){
		getData().put("spot_description",spotDescription);
		this.spotDescription=spotDescription;
	}
	
	public String getSpotDescription(){
		return (String)getData().get("spot_description");
	}	
	 }

