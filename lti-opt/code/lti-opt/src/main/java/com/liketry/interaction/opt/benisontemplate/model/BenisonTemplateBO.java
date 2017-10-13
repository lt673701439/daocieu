package com.liketry.interaction.opt.benisontemplate.model;

import java.util.Arrays;
import java.lang.String;
import com.taikang.udp.framework.common.datastructre.impl.BaseBO;


/**
  * BenisonTemplateBO 
  */
public class BenisonTemplateBO  extends BaseBO {
	
	private static final long serialVersionUID = 1L;
	
	public BenisonTemplateBO(){
		init();
	}
	
	protected void init(){
		super.init();
		this.addList(Arrays.asList("template_id","template_code","template_name","screen_id","type_id","benison_id","bg_img_id","sm_img_id","title_x","title_y","title_colour","title_size","title_type","body_x","body_y","body_colour","body_size","body_type","tail_x","tail_y","tail_colour","tail_size","tail_type","benison_content","img_url","img_code","rule_content"));
	}
	
		
		
	private String templateId;
		public void setTemplateId(String templateId){
		getData().put("template_id",templateId);
		this.templateId=templateId;
	}
	
	public String getTemplateId(){
		return (String)getData().get("template_id");
	}	
		
		
	private String templateCode;
		public void setTemplateCode(String templateCode){
		getData().put("template_code",templateCode);
		this.templateCode=templateCode;
	}
	
	public String getTemplateCode(){
		return (String)getData().get("template_code");
	}	
		
		
	private String templateName;
		public void setTemplateName(String templateName){
		getData().put("template_name",templateName);
		this.templateName=templateName;
	}
	
	public String getTemplateName(){
		return (String)getData().get("template_name");
	}	
		
		
	private String screenId;
		public void setScreenId(String screenId){
		getData().put("screen_id",screenId);
		this.screenId=screenId;
	}
	
	public String getScreenId(){
		return (String)getData().get("screen_id");
	}	
		
		
	private String typeId;
		public void setTypeId(String typeId){
		getData().put("type_id",typeId);
		this.typeId=typeId;
	}
	
	public String getTypeId(){
		return (String)getData().get("type_id");
	}	
		
		
	private String benisonId;
		public void setBenisonId(String benisonId){
		getData().put("benison_id",benisonId);
		this.benisonId=benisonId;
	}
	
	public String getBenisonId(){
		return (String)getData().get("benison_id");
	}	
		
		
	private String bgImgId;
		public void setBgImgId(String bgImgId){
		getData().put("bg_img_id",bgImgId);
		this.bgImgId=bgImgId;
	}
	
	public String getBgImgId(){
		return (String)getData().get("bg_img_id");
	}	
		
		
	private String smImgId;
		public void setSmImgId(String smImgId){
		getData().put("sm_img_id",smImgId);
		this.smImgId=smImgId;
	}
	
	public String getSmImgId(){
		return (String)getData().get("sm_img_id");
	}	
		
		
	private String titleX;
		public void setTitleX(String titleX){
		getData().put("title_x",titleX);
		this.titleX=titleX;
	}
	
	public String getTitleX(){
		return (String)getData().get("title_x");
	}	
		
		
	private String titleY;
		public void setTitleY(String titleY){
		getData().put("title_y",titleY);
		this.titleY=titleY;
	}
	
	public String getTitleY(){
		return (String)getData().get("title_y");
	}	
		
		
	private String titleColour;
		public void setTitleColour(String titleColour){
		getData().put("title_colour",titleColour);
		this.titleColour=titleColour;
	}
	
	public String getTitleColour(){
		return (String)getData().get("title_colour");
	}	
		
		
	private String titleSize;
		public void setTitleSize(String titleSize){
		getData().put("title_size",titleSize);
		this.titleSize=titleSize;
	}
	
	public String getTitleSize(){
		return (String)getData().get("title_size");
	}	
		
		
	private String titleType;
		public void setTitleType(String titleType){
		getData().put("title_type",titleType);
		this.titleType=titleType;
	}
	
	public String getTitleType(){
		return (String)getData().get("title_type");
	}	
		
		
	private String bodyX;
		public void setBodyX(String bodyX){
		getData().put("body_x",bodyX);
		this.bodyX=bodyX;
	}
	
	public String getBodyX(){
		return (String)getData().get("body_x");
	}	
		
		
	private String bodyY;
		public void setBodyY(String bodyY){
		getData().put("body_y",bodyY);
		this.bodyY=bodyY;
	}
	
	public String getBodyY(){
		return (String)getData().get("body_y");
	}	
		
		
	private String bodyColour;
		public void setBodyColour(String bodyColour){
		getData().put("body_colour",bodyColour);
		this.bodyColour=bodyColour;
	}
	
	public String getBodyColour(){
		return (String)getData().get("body_colour");
	}	
		
		
	private String bodySize;
		public void setBodySize(String bodySize){
		getData().put("body_size",bodySize);
		this.bodySize=bodySize;
	}
	
	public String getBodySize(){
		return (String)getData().get("body_size");
	}	
		
		
	private String bodyType;
		public void setBodyType(String bodyType){
		getData().put("body_type",bodyType);
		this.bodyType=bodyType;
	}
	
	public String getBodyType(){
		return (String)getData().get("body_type");
	}	
		
		
	private String tailX;
		public void setTailX(String tailX){
		getData().put("tail_x",tailX);
		this.tailX=tailX;
	}
	
	public String getTailX(){
		return (String)getData().get("tail_x");
	}	
		
		
	private String tailY;
		public void setTailY(String tailY){
		getData().put("tail_y",tailY);
		this.tailY=tailY;
	}
	
	public String getTailY(){
		return (String)getData().get("tail_y");
	}	
		
		
	private String tailColour;
		public void setTailColour(String tailColour){
		getData().put("tail_colour",tailColour);
		this.tailColour=tailColour;
	}
	
	public String getTailColour(){
		return (String)getData().get("tail_colour");
	}	
		
		
	private String tailSize;
		public void setTailSize(String tailSize){
		getData().put("tail_size",tailSize);
		this.tailSize=tailSize;
	}
	
	public String getTailSize(){
		return (String)getData().get("tail_size");
	}	
		
		
	private String tailType;
		public void setTailType(String tailType){
		getData().put("tail_type",tailType);
		this.tailType=tailType;
	}
	
	public String getTailType(){
		return (String)getData().get("tail_type");
	}	
	
	private String benisonContent;
	public void setBenisonContent(String benisonContent){
		getData().put("benison_content",benisonContent);
		this.benisonContent=benisonContent;
	}
	
	public String getBenisonContent(){
		return (String)getData().get("benison_content");
	}	
	
	private String imgUrl;
	public void setImgUrl(String imgUrl){
		getData().put("img_url",imgUrl);
		this.imgUrl=imgUrl;
	}
	
	public String getImgUrl(){
		return (String)getData().get("img_url");
	}
	
	private String imgCode;
	public void setImgCode(String imgCode){
		getData().put("img_code",imgCode);
		this.imgCode=imgCode;
	}
	
	public String getImgCode(){
		return (String)getData().get("img_code");
	}
	
	private String ruleContent;
	public void setRuleContent(String ruleContent){
		getData().put("rule_content",imgCode);
		this.ruleContent=ruleContent;
	}
	
	public String getRuleContent(){
		return (String)getData().get("rule_content");
	}
	 }

