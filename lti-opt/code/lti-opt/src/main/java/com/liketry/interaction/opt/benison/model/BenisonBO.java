package com.liketry.interaction.opt.benison.model;

import java.util.Arrays;
import java.lang.String;
import com.taikang.udp.framework.common.datastructre.impl.BaseBO;


/**
  * BenisonBO 
  */
public class BenisonBO  extends BaseBO {
	
	private static final long serialVersionUID = 1L;
	
	public BenisonBO(){
		init();
	}
	
	protected void init(){
		super.init();
		this.addList(Arrays.asList("benison_id","type_id","benison_code","benison_content"));
	}
	
		
		
	private String benisonId;
		public void setBenisonId(String benisonId){
		getData().put("benison_id",benisonId);
		this.benisonId=benisonId;
	}
	
	public String getBenisonId(){
		return (String)getData().get("benison_id");
	}	
		
		
	private String typeId;
		public void setTypeId(String typeId){
		getData().put("type_id",typeId);
		this.typeId=typeId;
	}
	
	public String getTypeId(){
		return (String)getData().get("type_id");
	}	
		
		
	private String benisonCode;
		public void setBenisonCode(String benisonCode){
		getData().put("benison_code",benisonCode);
		this.benisonCode=benisonCode;
	}
	
	public String getBenisonCode(){
		return (String)getData().get("benison_code");
	}	
		
		
	private String benisonContent;
		public void setBenisonContent(String benisonContent){
		getData().put("benison_content",benisonContent);
		this.benisonContent=benisonContent;
	}
	
	public String getBenisonContent(){
		return (String)getData().get("benison_content");
	}
	
	private String ruleContent;
	public void setRuleContent(String ruleContent){
		getData().put("rule_content",ruleContent);
		this.ruleContent=ruleContent;
	}
	
	public String getRuleContent(){
		return (String)getData().get("rule_content");
	}	

	 }

