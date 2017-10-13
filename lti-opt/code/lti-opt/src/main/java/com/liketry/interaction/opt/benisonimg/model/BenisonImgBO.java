package com.liketry.interaction.opt.benisonimg.model;

import java.util.Arrays;
import java.lang.String;
import com.taikang.udp.framework.common.datastructre.impl.BaseBO;


/**
  * BenisonImgBO 
  */
public class BenisonImgBO  extends BaseBO {
	
	private static final long serialVersionUID = 1L;
	
	public BenisonImgBO(){
		init();
	}
	
	protected void init(){
		super.init();
		this.addList(Arrays.asList("img_id","img_code","img_name","img_type","img_url"));
	}
	
		
		
	private String imgId;
		public void setImgId(String imgId){
		getData().put("img_id",imgId);
		this.imgId=imgId;
	}
	
	public String getImgId(){
		return (String)getData().get("img_id");
	}	
		
		
	private String imgCode;
		public void setImgCode(String imgCode){
		getData().put("img_code",imgCode);
		this.imgCode=imgCode;
	}
	
	public String getImgCode(){
		return (String)getData().get("img_code");
	}	
		
		
	private String imgName;
		public void setImgName(String imgName){
		getData().put("img_name",imgName);
		this.imgName=imgName;
	}
	
	public String getImgName(){
		return (String)getData().get("img_name");
	}	
		
		
	private String imgType;
		public void setImgType(String imgType){
		getData().put("img_type",imgType);
		this.imgType=imgType;
	}
	
	public String getImgType(){
		return (String)getData().get("img_type");
	}	
		
		
	private String imgUrl;
		public void setImgUrl(String imgUrl){
		getData().put("img_url",imgUrl);
		this.imgUrl=imgUrl;
	}
	
	public String getImgUrl(){
		return (String)getData().get("img_url");
	}	
	 }

