package com.liketry.interaction.opt.screen.model;

import java.util.Arrays;
import java.lang.String;
import com.taikang.udp.framework.common.datastructre.impl.BaseBO;


/**
  * ScreenBO 
  */
public class ScreenBO  extends BaseBO {
	
	private static final long serialVersionUID = 1L;
	
	public ScreenBO(){
		init();
	}
	
	protected void init(){
		super.init();
		this.addList(Arrays.asList("screen_id","spot_id","screen_code","screen_name","screen_status","screen_img","screen_location","screen_longitude","screen_dimension","screen_long","screen_wide","screen_size","screen_resolution","screen_description"));
	}
	
		
		
	private String screenId;
		public void setScreenId(String screenId){
		getData().put("screen_id",screenId);
		this.screenId=screenId;
	}
	
	public String getScreenId(){
		return (String)getData().get("screen_id");
	}	
		
		
	private String spotId;
		public void setSpotId(String spotId){
		getData().put("spot_id",spotId);
		this.spotId=spotId;
	}
	
	public String getSpotId(){
		return (String)getData().get("spot_id");
	}	
		
		
	private String screenCode;
		public void setScreenCode(String screenCode){
		getData().put("screen_code",screenCode);
		this.screenCode=screenCode;
	}
	
	public String getScreenCode(){
		return (String)getData().get("screen_code");
	}	
		
		
	private String screenName;
		public void setScreenName(String screenName){
		getData().put("screen_name",screenName);
		this.screenName=screenName;
	}
	
	public String getScreenName(){
		return (String)getData().get("screen_name");
	}	
		
		
	private String screenStatus;
		public void setScreenStatus(String screenStatus){
		getData().put("screen_status",screenStatus);
		this.screenStatus=screenStatus;
	}
	
	public String getScreenStatus(){
		return (String)getData().get("screen_status");
	}	
		
		
	private String screenImg;
		public void setScreenImg(String screenImg){
		getData().put("screen_img",screenImg);
		this.screenImg=screenImg;
	}
	
	public String getScreenImg(){
		return (String)getData().get("screen_img");
	}	
		
		
	private String screenLocation;
		public void setScreenLocation(String screenLocation){
		getData().put("screen_location",screenLocation);
		this.screenLocation=screenLocation;
	}
	
	public String getScreenLocation(){
		return (String)getData().get("screen_location");
	}	
		
		
	private String screenLongitude;
		public void setScreenLongitude(String screenLongitude){
		getData().put("screen_longitude",screenLongitude);
		this.screenLongitude=screenLongitude;
	}
	
	public String getScreenLongitude(){
		return (String)getData().get("screen_longitude");
	}	
		
		
	private String screenDimension;
		public void setScreenDimension(String screenDimension){
		getData().put("screen_dimension",screenDimension);
		this.screenDimension=screenDimension;
	}
	
	public String getScreenDimension(){
		return (String)getData().get("screen_dimension");
	}	
		
		
	private String screenLong;
		public void setScreenLong(String screenLong){
		getData().put("screen_long",screenLong);
		this.screenLong=screenLong;
	}
	
	public String getScreenLong(){
		return (String)getData().get("screen_long");
	}	
		
		
	private String screenWide;
		public void setScreenWide(String screenWide){
		getData().put("screen_wide",screenWide);
		this.screenWide=screenWide;
	}
	
	public String getScreenWide(){
		return (String)getData().get("screen_wide");
	}	
		
		
	private String screenSize;
		public void setScreenSize(String screenSize){
		getData().put("screen_size",screenSize);
		this.screenSize=screenSize;
	}
	
	public String getScreenSize(){
		return (String)getData().get("screen_size");
	}	
		
		
	private String screenResolution;
		public void setScreenResolution(String screenResolution){
		getData().put("screen_resolution",screenResolution);
		this.screenResolution=screenResolution;
	}
	
	public String getScreenResolution(){
		return (String)getData().get("screen_resolution");
	}	
		
		
	private String screenDescription;
		public void setScreenDescription(String screenDescription){
		getData().put("screen_description",screenDescription);
		this.screenDescription=screenDescription;
	}
	
	public String getScreenDescription(){
		return (String)getData().get("screen_description");
	}	
	 }

