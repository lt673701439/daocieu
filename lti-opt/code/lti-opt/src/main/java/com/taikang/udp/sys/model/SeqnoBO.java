package com.taikang.udp.sys.model;

import java.lang.Integer;
import java.lang.String;
import java.util.Arrays;
import com.taikang.udp.framework.common.datastructre.impl.BaseBO;


/**
  * SeqnoBO 
  */
public class SeqnoBO  extends BaseBO {
	
	private static final long serialVersionUID = 1L;
	
	public SeqnoBO(){
		init();
	}
	
	protected void init(){
		super.init();
		this.addList(Arrays.asList("seq_type","seq_prefix","place_suffix","place_holder","align_type","curr_value","t_increment","init_value","max_value","max_length","cache_num","t_explain"));
	}
	
		
		
	private String seqType;
		public void setSeqType(String seqType){
		getData().put("seq_type",seqType);
		this.seqType=seqType;
	}
	
	public String getSeqType(){
		return (String)getData().get("seq_type");
	}	
		
		
	private String seqPrefix;
		public void setSeqPrefix(String seqPrefix){
		getData().put("seq_prefix",seqPrefix);
		this.seqPrefix=seqPrefix;
	}
	
	public String getSeqPrefix(){
		return (String)getData().get("seq_prefix");
	}	
		
		
	private String placeSuffix;
		public void setPlaceSuffix(String placeSuffix){
		getData().put("place_suffix",placeSuffix);
		this.placeSuffix=placeSuffix;
	}
	
	public String getPlaceSuffix(){
		return (String)getData().get("place_suffix");
	}	
		
		
	private String placeHolder;
		public void setPlaceHolder(String placeHolder){
		getData().put("place_holder",placeHolder);
		this.placeHolder=placeHolder;
	}
	
	public String getPlaceHolder(){
		return (String)getData().get("place_holder");
	}	
		
		
	private String alignType;
		public void setAlignType(String alignType){
		getData().put("align_type",alignType);
		this.alignType=alignType;
	}
	
	public String getAlignType(){
		return (String)getData().get("align_type");
	}	
		
		
	private Integer currValue;
		public void setCurrValue(Integer currValue){
		getData().put("curr_value",currValue);
		this.currValue=currValue;
	}
	
	public Integer getCurrValue(){
		return (Integer)getData().get("curr_value");
	}	
		
		
	private Integer tIncrement;
		public void setTIncrement(Integer tIncrement){
		getData().put("t_increment",tIncrement);
		this.tIncrement=tIncrement;
	}
	
	public Integer getTIncrement(){
		return (Integer)getData().get("t_increment");
	}	
		
		
	private Integer initValue;
		public void setInitValue(Integer initValue){
		getData().put("init_value",initValue);
		this.initValue=initValue;
	}
	
	public Integer getInitValue(){
		return (Integer)getData().get("init_value");
	}	
		
		
	private Integer maxValue;
		public void setMaxValue(Integer maxValue){
		getData().put("max_value",maxValue);
		this.maxValue=maxValue;
	}
	
	public Integer getMaxValue(){
		return (Integer)getData().get("max_value");
	}	
		
		
	private Integer maxLength;
		public void setMaxLength(Integer maxLength){
		getData().put("max_length",maxLength);
		this.maxLength=maxLength;
	}
	
	public Integer getMaxLength(){
		return (Integer)getData().get("max_length");
	}	
		
		
	private Integer cacheNum;
		public void setCacheNum(Integer cacheNum){
		getData().put("cache_num",cacheNum);
		this.cacheNum=cacheNum;
	}
	
	public Integer getCacheNum(){
		return (Integer)getData().get("cache_num");
	}	
		
		
	private String tExplain;
		public void setTExplain(String tExplain){
		getData().put("t_explain",tExplain);
		this.tExplain=tExplain;
	}
	
	public String getTExplain(){
		return (String)getData().get("t_explain");
	}	
	 }

