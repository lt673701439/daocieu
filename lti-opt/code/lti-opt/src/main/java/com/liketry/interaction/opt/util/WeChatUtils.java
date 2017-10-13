package com.liketry.interaction.opt.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import com.taikang.udp.framework.core.properties.PropertiesFile;
import com.taikang.udp.framework.core.properties.PropertiesHandler;
import com.taikang.udp.framework.core.properties.PropertiesHandlerFactory;

/**
 * 微信支付工具类
 * 
 * @author wuke
 */
public class WeChatUtils {
	
	private static final Logger log = LoggerFactory.getLogger(WeChatUtils.class);
	
	protected static PropertiesHandler initProperty = PropertiesHandlerFactory.getPropertiesHelper(PropertiesFile.FRAMEWORK);
	
	  /**
	   * 随机字符串生成
	   * @param parameters
	   * @return
	   */
      public static String getRandomString(int length) { //length表示生成字符串的长度      
           String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";         
           Random random = new Random();         
           StringBuffer sb = new StringBuffer();         
           for (int i = 0; i < length; i++) {         
               int number = random.nextInt(base.length());         
               sb.append(base.charAt(number));         
           }         
           return sb.toString();         
      }    
      
	  /**
	   * 将元转换位分 
	   * @param parameters
	   * @return
	   */
      public static int getCentsByYuan(String total_price) {    
		
    	  double total_fee =0;
		try{
			total_fee=Double.parseDouble(total_price); // 0.01元
		}catch (Exception e) {
			total_fee=0;
		}

		return (int) (total_fee*100);
      }
      
	  /**
	   * 请求xml组装
	   * @param parameters
	   * @return
	   */
      public static String getRequestXml(SortedMap<String,Object> parameters){  
            StringBuffer sb = new StringBuffer();  
            sb.append("<xml>");  
            Set es = parameters.entrySet();  
            Iterator it = es.iterator();  
            while(it.hasNext()) {  
                Map.Entry entry = (Map.Entry)it.next();  
                String key = entry.getKey().toString();
                
                // 当value等于null时，将排除此项
                if(entry.getValue() == null){
                	continue;
                }
                
                String value = entry.getValue().toString();  
                if ("attach".equalsIgnoreCase(key)||"body".equalsIgnoreCase(key)||"sign".equalsIgnoreCase(key)) {  
                    sb.append("<"+key+">"+"<![CDATA["+value+"]]></"+key+">");  
                }else {  
                    sb.append("<"+key+">"+value+"</"+key+">");  
                }  
            }  
            sb.append("</xml>");  
            return sb.toString();  
      } 

	  /**
	   * 获取10位时间戳
	   * @return
	   */
//      public static String getTimestamp10(){  
//    	  	// 时间戳
//    	  	Date date = new Date();
//    	  	long time = date.getTime();
//    	  	
//    	  	// 时间戳只有10位
//    	  	String dateline = time + "";
//            return dateline.substring(0, 10);
//      }

      
	  /**
	   * 微信支付签名算法sign
	   * @param characterEncoding
	   * @param parameters
	   * @param apiKey
	   * @return
	   */
	  @SuppressWarnings("rawtypes")
	  public static String createSign(String characterEncoding,SortedMap<String,Object> parameters, String apiKey){
	 
	    StringBuffer sb = new StringBuffer();
	    Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）
	    Iterator it = es.iterator();
	    while(it.hasNext()) {
	      Map.Entry entry = (Map.Entry)it.next();
	      String k = (String)entry.getKey();
	      Object v = entry.getValue();
	      if(null != v && !"".equals(v) 
	          && !"sign".equals(k) && !"key".equals(k)) {
	        sb.append(k + "=" + v + "&");
	      }
	    }
	    sb.append("key=" + apiKey);
	    log.info("签名加密前的；字符串是："+sb.toString());
	    String sign = MD5Utils.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
	    return sign;
	  }
	  
      
      /**
       * 获得对账单数据
       * @param 对账开始日期(2014-06-03)
       * @param 对账结束日期(2014-06-03)
       * @param 对账类型
       * @param 交易类型(APP/JS)
       */
      public static List<Dto> getBillDataList(String start_date, String end_date, String bill_type, String trade_type) { 
    	  	
    	    List<Dto> dataList = new ArrayList();
	  		
    	  	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
    	  	Date startDate = new Date();
    		Date endDate = new Date();
    		
    		try {
    			startDate = dateFormat.parse(start_date);
    			endDate = dateFormat.parse(end_date);
    		} catch (ParseException e) {
    			e.printStackTrace();
    		}
    	  	
    	  	//循环日期
			Calendar ca = Calendar.getInstance();
			Date curDate = startDate;
			while(curDate.compareTo(endDate)<=0){
			      ca.setTime(curDate);
			      //业务处理
			      setBillDataList(dataList, dateFormat.format(curDate), bill_type, trade_type);
			      
			      ca.add(ca.DATE, 1);
			      curDate = ca.getTime();
			}
			
			return dataList;
      }
      
      /**
       * 为对账单list添加单条数据
       * @param 数据列表
       * @param 对账日期(20140603)
       * @param 对账类型
       * @param 交易类型(APP/JS)
       */
      public static void setBillDataList(List<Dto> dataList, String billDate, String bill_type, String trade_type) { 
    	  
    	  	if(dataList == null || billDate == null || bill_type == null){
	  			log.info("setBillDataList方法，有参数为null，调用失败！");
	  			return;
	  		}
    	  	
    		// 调整日期格式为20140603
    		billDate = billDate.replaceAll("-", "");
    	  
    	  	// 调用微信接口，获取对账单字符串
	  		String billStr = WeChatUtils.wechatDownloadBill(billDate, bill_type, trade_type);
	  		
	  		if(billStr == null || "FAIL".equals(billStr)){
	  			return;
	  		}
    	  
	    	// 解析对账单字符串
	  		billStr = billStr.replaceAll(",", "");  						// 去逗号
	  		int dataStart = billStr.indexOf("`") + 1;						// 取数据的首字符的位置
	  		int totalStart = billStr.lastIndexOf("%") + 1;					// 取汇总的首字符的位置
	  		String billDataStr = billStr.substring(dataStart,totalStart - 1);  	// 数据字段
	  		String[] billDataArr = billDataStr.split("%`"); 				// 数据分组
	  		
	  		// 组装数据
	  		for(String dataStr : billDataArr){
	  			Dto dto = new BaseDto();
	  			String[] data = dataStr.split("`");
	  			
	  			// 交易时间,公众账号ID,商户号,子商户号,设备号,微信订单号,商户订单号,用户标识,交易类型,交易状态,付款银行,
	  			// 货币种类,总金额,企业红包金额,微信退款单号,商户退款单号,退款金额,企业红包退款金额,退款类型,退款状态,商品名称,商户数据包,手续费,费率
	  			dto.put("pay_time", data[0]);		// ﻿交易时间
	  			dto.put("mch_id", data[2]);			// 商户号
	  			dto.put("order_code", data[6]);		// 商户订单号/订单编号
	  			dto.put("transaction_no", data[5]);	// 微信订单号/交易单号
	  			dto.put("pay_type", data[8]);		// 交易类型
	  			dto.put("pay_status", ("SUCCESS".equals(data[9]) ? "已支付" : "已退款"));// 交易状态
	  			dto.put("pay_price", data[12]);		// 总金额/交易金额(元)
	  			dto.put("back_price", data[16]);	// 退款金额
	  			dataList.add(dto);
	  		}
      }
      
      /**
       * 微信（APP）-对账单下载
       * @param 对账日期(20140603)
       * @param 对账类型
       * @param 交易类型(APP/JS)
       */
      public static String wechatDownloadBill(String bill_date, String bill_type, String trade_type) {  
    	  	
    	  	// 整理参数
            String appid = initProperty.getValue("appid");		// 微信开放平台审核通过的应用APPID（APP）
            String mch_id = initProperty.getValue("mch_id");	// 微信支付分配的商户号（APP）
            String apiKey = initProperty.getValue("api_key");	// api秘钥（APP）
            
            if("JS".equals(trade_type)){
            	appid = initProperty.getValue("appid_js");		// 微信开放平台审核通过的应用APPID（小程序）
            	mch_id = initProperty.getValue("mch_id_js");	// 微信支付分配的商户号（小程序）
            	apiKey = initProperty.getValue("api_key_js");	// api秘钥（小程序）
            }
            
            
    	    String nonce_str = WeChatUtils.getRandomString(32);	// 32位的随机数
    	    
    	    // 组装参数并生成签名
    	    SortedMap<String,Object> parameters = new TreeMap<String,Object>();
    	    parameters.put("appid", appid);
    	    parameters.put("mch_id", mch_id);
    	    parameters.put("nonce_str", nonce_str);
    	    parameters.put("bill_date", bill_date);
    	    parameters.put("bill_type", bill_type);
    	    	
    	    // 生成签名
    	    String sign = WeChatUtils.createSign("UTF-8", parameters, apiKey);
    	    parameters.put("sign", sign);
    	    
    	    log.info("调用【微信（APP）-对账单下载】接口的参数为：" + parameters.toString());
    	    
    	    // 构造xml参数
    	    String xmlInfo = WeChatUtils.getRequestXml(parameters);
    	    
    	    String wxUrl = "https://api.mch.weixin.qq.com/pay/downloadbill";
    		 
    	    String method = "POST";
    	 
    	    String wxRsult = HttpXmlUtils.httpsRequest(wxUrl, method, xmlInfo).toString();
    	 
    	    log.info("调用【微信（APP）-对账单下载】接口的结果为：" + wxRsult);
    	    
    	    if(wxRsult == null || wxRsult.startsWith("<xml>")){
    	    	log.info("调用【微信（APP）-对账单下载】接口调用出现问题！请查看调用结果。");
    	    	return "FAIL";
    	    }
            
            return wxRsult;
      }
}
