package com.liketry.interaction.opt.commoditycost.model;

import java.math.BigDecimal;
import java.util.Arrays;
import java.lang.String;
import com.taikang.udp.framework.common.datastructre.impl.BaseBO;


/**
  * CommodityCostBO 
  */
public class CommodityCostBO  extends BaseBO {
	
	private static final long serialVersionUID = 1L;
	
	public CommodityCostBO(){
		init();
	}
	
	protected void init(){
		super.init();
		this.addList(Arrays.asList("cost_id","commodity_id","sku_id","cost_type","cost_name","cost_price"));
	}
	
		
		
	private String costId;
		public void setCostId(String costId){
		getData().put("cost_id",costId);
		this.costId=costId;
	}
	
	public String getCostId(){
		return (String)getData().get("cost_id");
	}	
		
		
	private String commodityId;
		public void setCommodityId(String commodityId){
		getData().put("commodity_id",commodityId);
		this.commodityId=commodityId;
	}
	
	public String getCommodityId(){
		return (String)getData().get("commodity_id");
	}	
		
		
	private String skuId;
		public void setSkuId(String skuId){
		getData().put("sku_id",skuId);
		this.skuId=skuId;
	}
	
	public String getSkuId(){
		return (String)getData().get("sku_id");
	}	
		
		
	private String costType;
		public void setCostType(String costType){
		getData().put("cost_type",costType);
		this.costType=costType;
	}
	
	public String getCostType(){
		return (String)getData().get("cost_type");
	}	
		
		
	private String costName;
		public void setCostName(String costName){
		getData().put("cost_name",costName);
		this.costName=costName;
	}
	
	public String getCostName(){
		return (String)getData().get("cost_name");
	}	
		
		
	private BigDecimal costPrice;
		public void setCostPrice(BigDecimal costPrice){
		getData().put("cost_price",costPrice);
		this.costPrice=costPrice;
	}
	
	public BigDecimal getCostPrice(){
		return (BigDecimal)getData().get("cost_price");
	}	
	 }

