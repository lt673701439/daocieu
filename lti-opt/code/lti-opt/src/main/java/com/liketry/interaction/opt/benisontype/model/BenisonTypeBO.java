package com.liketry.interaction.opt.benisontype.model;

import java.util.Arrays;
import java.lang.String;
import java.lang.Integer;
import com.taikang.udp.framework.common.datastructre.impl.BaseBO;


/**
  * BenisonTypeBO 
  */
public class BenisonTypeBO  extends BaseBO {
	
	private static final long serialVersionUID = 1L;
	
	public BenisonTypeBO(){
		init();
	}
	
	protected void init(){
		super.init();
		this.addList(Arrays.asList("type_id","type_code","type_name","effect_flag","sort_num"));
	}
	
		
		
	private String typeId;
		public void setTypeId(String typeId){
		getData().put("type_id",typeId);
		this.typeId=typeId;
	}
	
	public String getTypeId(){
		return (String)getData().get("type_id");
	}	
		
		
	private String typeCode;
		public void setTypeCode(String typeCode){
		getData().put("type_code",typeCode);
		this.typeCode=typeCode;
	}
	
	public String getTypeCode(){
		return (String)getData().get("type_code");
	}	
		
		
	private String typeName;
		public void setTypeName(String typeName){
		getData().put("type_name",typeName);
		this.typeName=typeName;
	}
	
	public String getTypeName(){
		return (String)getData().get("type_name");
	}	
		
		
	private String effectFlag;
		public void setEffectFlag(String effectFlag){
		getData().put("effect_flag",effectFlag);
		this.effectFlag=effectFlag;
	}
	
	public String getEffectFlag(){
		return (String)getData().get("effect_flag");
	}	
		
		
	private Integer sortNum;
		public void setSortNum(Integer sortNum){
		getData().put("sort_num",sortNum);
		this.sortNum=sortNum;
	}
	
	public Integer getSortNum(){
		return (Integer)getData().get("sort_num");
	}	
	 }

