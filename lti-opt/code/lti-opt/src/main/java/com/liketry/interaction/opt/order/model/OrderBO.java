package com.liketry.interaction.opt.order.model;

import java.math.BigDecimal;
import java.util.Arrays;
import java.sql.Timestamp;
import java.lang.String;
import java.lang.Integer;
import com.taikang.udp.framework.common.datastructre.impl.BaseBO;


/**
  * OrderBO 
  */
public class OrderBO  extends BaseBO {
	
	private static final long serialVersionUID = 1L;
	
	public OrderBO(){
		init();
	}
	
	protected void init(){
		super.init();
		this.addList(Arrays.asList("order_id","order_code","transaction_no","order_type","order_status","user_id","scene","user_nickname","user_phone","bless_name","write_name","template_id","screen_id","screen_name","type_id","type_name","benison_id","benison_content","rule_content","bkimg_id","bkimg_name","bkimg_url","total_price","total_orig_price","pay_type","pay_time","pay_price","confirm_status","confirm_time","confirm_by","confirm_id","confirm_reason","back_time","back_by","back_id","back_reason","back_price","back_remarks","order_descriptiion","export_by","export_id","export_time","result_url","share_url","promotion_id","created_time","created_by","modified_time","modified_by","version","delflag","template_name"));
	}
	
		
		
	private String orderId;
	public void setOrderId(String orderId){
		getData().put("order_id",orderId);
		this.orderId=orderId;
	}
	
	public String getOrderId(){
		return (String)getData().get("order_id");
	}	
		
		
	private String orderCode;
		public void setOrderCode(String orderCode){
		getData().put("order_code",orderCode);
		this.orderCode=orderCode;
	}
	
	public String getOrderCode(){
		return (String)getData().get("order_code");
	}	
		
		
	private String transactionNo;
		public void setTransactionNo(String transactionNo){
		getData().put("transaction_no",transactionNo);
		this.transactionNo=transactionNo;
	}
	
	public String getTransactionNo(){
		return (String)getData().get("transaction_no");
	}	
		
		
	private String orderType;
		public void setOrderType(String orderType){
		getData().put("order_type",orderType);
		this.orderType=orderType;
	}
	
	public String getOrderType(){
		return (String)getData().get("order_type");
	}	
		
		
	private String orderStatus;
		public void setOrderStatus(String orderStatus){
		getData().put("order_status",orderStatus);
		this.orderStatus=orderStatus;
	}
	
	public String getOrderStatus(){
		return (String)getData().get("order_status");
	}	
		
		
	private String userId;
		public void setUserId(String userId){
		getData().put("user_id",userId);
		this.userId=userId;
	}
	
	public String getUserId(){
		return (String)getData().get("user_id");
	}
	
	private String scene;
	public void setScene(String scene){
		getData().put("scene",scene);
		this.scene=scene;
	}
	
	public String getScene(){
		return (String)getData().get("scene");
	}
		
		
	private String userNickname;
		public void setUserNickname(String userNickname){
		getData().put("user_nickname",userNickname);
		this.userNickname=userNickname;
	}
	
	public String getUserNickname(){
		return (String)getData().get("user_nickname");
	}	
		
		
	private String userPhone;
		public void setUserPhone(String userPhone){
		getData().put("user_phone",userPhone);
		this.userPhone=userPhone;
	}
	
	public String getUserPhone(){
		return (String)getData().get("user_phone");
	}	
		
		
	private String blessName;
		public void setBlessName(String blessName){
		getData().put("bless_name",blessName);
		this.blessName=blessName;
	}
	
	public String getBlessName(){
		return (String)getData().get("bless_name");
	}	
		
		
	private String writeName;
		public void setWriteName(String writeName){
		getData().put("write_name",writeName);
		this.writeName=writeName;
	}
	
	public String getWriteName(){
		return (String)getData().get("write_name");
	}	
		
		
	private String templateId;
		public void setTemplateId(String templateId){
		getData().put("template_id",templateId);
		this.templateId=templateId;
	}
	
	public String getTemplateId(){
		return (String)getData().get("template_id");
	}	
		
		
	private String screenId;
		public void setScreenId(String screenId){
		getData().put("screen_id",screenId);
		this.screenId=screenId;
	}
	
	public String getScreenId(){
		return (String)getData().get("screen_id");
	}	
		
		
	private String screenName;
		public void setScreenName(String screenName){
		getData().put("screen_name",screenName);
		this.screenName=screenName;
	}
	
	public String getScreenName(){
		return (String)getData().get("screen_name");
	}	
		
		
	private String typeId;
		public void setTypeId(String typeId){
		getData().put("type_id",typeId);
		this.typeId=typeId;
	}
	
	public String getTypeId(){
		return (String)getData().get("type_id");
	}	
		
		
	private String typeName;
		public void setTypeName(String typeName){
		getData().put("type_name",typeName);
		this.typeName=typeName;
	}
	
	public String getTypeName(){
		return (String)getData().get("type_name");
	}	
		
		
	private String benisonId;
		public void setBenisonId(String benisonId){
		getData().put("benison_id",benisonId);
		this.benisonId=benisonId;
	}
	
	public String getBenisonId(){
		return (String)getData().get("benison_id");
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
		
		
	private String bkimgId;
		public void setBkimgId(String bkimgId){
		getData().put("bkimg_id",bkimgId);
		this.bkimgId=bkimgId;
	}
	
	public String getBkimgId(){
		return (String)getData().get("bkimg_id");
	}	
		
		
	private String bkimgName;
		public void setBkimgName(String bkimgName){
		getData().put("bkimg_name",bkimgName);
		this.bkimgName=bkimgName;
	}
	
	public String getBkimgName(){
		return (String)getData().get("bkimg_name");
	}	
		
		
	private String bkimgUrl;
		public void setBkimgUrl(String bkimgUrl){
		getData().put("bkimg_url",bkimgUrl);
		this.bkimgUrl=bkimgUrl;
	}
	
	public String getBkimgUrl(){
		return (String)getData().get("bkimg_url");
	}	
		
		
	private BigDecimal totalPrice;
		public void setTotalPrice(BigDecimal totalPrice){
		getData().put("total_price",totalPrice);
		this.totalPrice=totalPrice;
	}
	
	public BigDecimal getTotalPrice(){
		return (BigDecimal)getData().get("total_price");
	}	
		
		
	private BigDecimal totalOrigPrice;
		public void setTotalOrigPrice(BigDecimal totalOrigPrice){
		getData().put("total_orig_price",totalOrigPrice);
		this.totalOrigPrice=totalOrigPrice;
	}
	
	public BigDecimal getTotalOrigPrice(){
		return (BigDecimal)getData().get("total_orig_price");
	}	
		
		
	private String payType;
		public void setPayType(String payType){
		getData().put("pay_type",payType);
		this.payType=payType;
	}
	
	public String getPayType(){
		return (String)getData().get("pay_type");
	}	
		
		
	private String payTime;
		public void setPayTime(String payTime){
		getData().put("pay_time",payTime);
		this.payTime=payTime;
	}
	
	public String getPayTime(){
		return (String)getData().get("pay_time");
	}	
		
		
	private BigDecimal payPrice;
		public void setPayPrice(BigDecimal payPrice){
		getData().put("pay_price",payPrice);
		this.payPrice=payPrice;
	}
	
	public BigDecimal getPayPrice(){
		return (BigDecimal)getData().get("pay_price");
	}	
		
		
	private String confirmStatus;
		public void setConfirmStatus(String confirmStatus){
		getData().put("confirm_status",confirmStatus);
		this.confirmStatus=confirmStatus;
	}
	
	public String getConfirmStatus(){
		return (String)getData().get("confirm_status");
	}	
		
		
	private String confirmTime;
		public void setConfirmTime(String confirmTime){
		getData().put("confirm_time",confirmTime);
		this.confirmTime=confirmTime;
	}
	
	public String getConfirmTime(){
		return (String)getData().get("confirm_time");
	}	
		
		
	private String confirmBy;
		public void setConfirmBy(String confirmBy){
		getData().put("confirm_by",confirmBy);
		this.confirmBy=confirmBy;
	}
	
	public String getConfirmBy(){
		return (String)getData().get("confirm_by");
	}	
		
		
	private String confirmId;
		public void setConfirmId(String confirmId){
		getData().put("confirm_id",confirmId);
		this.confirmId=confirmId;
	}
	
	public String getConfirmId(){
		return (String)getData().get("confirm_id");
	}	
		
		
	private String confirmReason;
		public void setConfirmReason(String confirmReason){
		getData().put("confirm_reason",confirmReason);
		this.confirmReason=confirmReason;
	}
	
	public String getConfirmReason(){
		return (String)getData().get("confirm_reason");
	}	
		
		
	private String backTime;
		public void setBackTime(String backTime){
		getData().put("back_time",backTime);
		this.backTime=backTime;
	}
	
	public String getBackTime(){
		return (String)getData().get("back_time");
	}	
		
		
	private String backBy;
		public void setBackBy(String backBy){
		getData().put("back_by",backBy);
		this.backBy=backBy;
	}
	
	public String getBackBy(){
		return (String)getData().get("back_by");
	}	
		
		
	private String backId;
		public void setBackId(String backId){
		getData().put("back_id",backId);
		this.backId=backId;
	}
	
	public String getBackId(){
		return (String)getData().get("back_id");
	}	
		
		
	private String backReason;
		public void setBackReason(String backReason){
		getData().put("back_reason",backReason);
		this.backReason=backReason;
	}
	
	public String getBackReason(){
		return (String)getData().get("back_reason");
	}	
		
		
	private BigDecimal backPrice;
		public void setBackPrice(BigDecimal backPrice){
		getData().put("back_price",backPrice);
		this.backPrice=backPrice;
	}
	
	public BigDecimal getBackPrice(){
		return (BigDecimal)getData().get("back_price");
	}	
		
		
	private String backRemarks;
		public void setBackRemarks(String backRemarks){
		getData().put("back_remarks",backRemarks);
		this.backRemarks=backRemarks;
	}
	
	public String getBackRemarks(){
		return (String)getData().get("back_remarks");
	}	
		
		
	private String orderDescriptiion;
		public void setOrderDescriptiion(String orderDescriptiion){
		getData().put("order_descriptiion",orderDescriptiion);
		this.orderDescriptiion=orderDescriptiion;
	}
	
	public String getOrderDescriptiion(){
		return (String)getData().get("order_descriptiion");
	}	
		
		
	private String exportBy;
		public void setExportBy(String exportBy){
		getData().put("export_by",exportBy);
		this.exportBy=exportBy;
	}
	
	public String getExportBy(){
		return (String)getData().get("export_by");
	}	
		
		
	private String exportId;
		public void setExportId(String exportId){
		getData().put("export_id",exportId);
		this.exportId=exportId;
	}
	
	public String getExportId(){
		return (String)getData().get("export_id");
	}	
		
		
	private String exportTime;
		public void setExportTime(String exportTime){
		getData().put("export_time",exportTime);
		this.exportTime=exportTime;
	}
	
	public String getExportTime(){
		return (String)getData().get("export_time");
	}	
		
		
	private String resultUrl;
		public void setResultUrl(String resultUrl){
		getData().put("result_url",resultUrl);
		this.resultUrl=resultUrl;
	}
	
	public String getResultUrl(){
		return (String)getData().get("result_url");
	}	
		
		
	private String shareUrl;
		public void setShareUrl(String shareUrl){
		getData().put("share_url",shareUrl);
		this.shareUrl=shareUrl;
	}
	
	public String getShareUrl(){
		return (String)getData().get("share_url");
	}	
		
		
	private String promotionId;
		public void setPromotionId(String promotionId){
		getData().put("promotion_id",promotionId);
		this.promotionId=promotionId;
	}
	
	public String getPromotionId(){
		return (String)getData().get("promotion_id");
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
	
	private String templateName;
	public void setTemplateName(String templateName){
		getData().put("template_name",templateName);
		this.templateName=templateName;
	}
	
	public String getTemplateName(){
		return (String)getData().get("template_name");
	}
}

