package com.liketry.interaction.benison.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minidev.json.JSONUtil;
 
/**
 * 微信支付工具类
 * 
 * @author wuke
 */
public class WeChatUtils {
	
	private static final Logger log = LoggerFactory.getLogger(WeChatUtils.class);
	 
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
	   * 验证回调签名
	   * @param characterEncoding
	   * @param map
	   * @param apiKey
	   * @return
	   */
      public static boolean isFormerSign(String characterEncoding, Map<String, String> map){
    	  
    	  String signFromAPIResponse = map.get("sign");
    	  if (signFromAPIResponse == null || signFromAPIResponse.equals("")) {
    		  log.info("API返回的数据签名数据不存在，有可能被第三方篡改!!!");
    		  return false;
    	  }
    	  
    	  log.info("服务器回包里面的签名是:" + signFromAPIResponse);
    	  
    	  //过滤空 设置 TreeMap
    	  SortedMap<String,String> packageParams = new TreeMap<>();
    	  
    	  for (String parameter : map.keySet()) {
    		  String parameterValue = map.get(parameter);
    		  String v = "";
    		  if (null != parameterValue) {
    			  v = parameterValue.trim();
    		  }
    		  packageParams.put(parameter, v);
    	  }

    	  StringBuffer sb = new StringBuffer();
          Set es = packageParams.entrySet();
          Iterator it = es.iterator();
          while(it.hasNext()) {
                Map.Entry entry = (Map.Entry)it.next();
                String k = (String)entry.getKey();
                String v = (String)entry.getValue();
                if(!"sign".equals(k) && null != v && !"".equals(v)) {
                    sb.append(k + "=" + v + "&");
                }
          }
          
          String apiKey = PropertiesUtils.getInstance().getValue("api_key");	// api秘钥（APP）
          if("JSAPI".equals(map.get("trade_type"))){
        	  apiKey = PropertiesUtils.getInstance().getValue("api_key_js");	// api秘钥（小程序）
          }
          
          sb.append("key=" + apiKey);
            
          //将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
          //算出签名
          String resultSign = "";
          String tobesign = sb.toString();
          resultSign = MD5Utils.MD5Encode(tobesign, characterEncoding).toUpperCase();
            
          String tenpaySign = ((String)packageParams.get("sign")).toUpperCase();
          return tenpaySign.equals(resultSign);
      }

      /**
       * 微信（APP）-申请退款
       * @param 订单编号
       * @param 支付价格
       */
      public static String wechatRefund(String orderCode, String payPrice) throws Exception {  
            
    	  	String mch_id = PropertiesUtils.getInstance().getValue("mch_id");	// 微信支付分配的商户号（APP）
    	  	
    	  	// 调用证书
    	  	KeyStore keyStore  = KeyStore.getInstance("PKCS12");  
            StringBuilder res = new StringBuilder("");  
            FileInputStream instream = new FileInputStream(new File(PropertiesUtils.getInstance().getValue("apiclient")));  
            try {  
                keyStore.load(instream, mch_id.toCharArray());
            } finally {  
                instream.close();
            }  
  
            // Trust own CA and all self-signed certs  
            SSLContext sslcontext = SSLContexts.custom()
                    .loadKeyMaterial(keyStore, mch_id.toCharArray())  
                    .build();
            // Allow TLSv1 protocol only  
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(  
            		sslcontext,
                    new String[] { "TLSv1" },
                    null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);  
            CloseableHttpClient httpclient = HttpClients.custom()  
                    .setSSLSocketFactory(sslsf)  
                    .build();
            
            // 整理参数，调用微信接口
            try {  
                // 整理参数
                String appid = PropertiesUtils.getInstance().getValue("appid");		// 微信开放平台审核通过的应用APPID（APP）
                String apiKey = PropertiesUtils.getInstance().getValue("api_key");	// api秘钥（APP）
        	    String nonce_str = WeChatUtils.getRandomString(32);					// 32位的随机数
        	    String out_trade_no = orderCode;									// 订单编号
        	    String out_refund_no = orderCode;									// 商户系统内部的退款单号
        	    int total_fee = WeChatUtils.getCentsByYuan(payPrice);				// 订单金额（分）
        	    int refund_fee = total_fee;											// 退款金额（分）
        	    
        	    // 组装参数并生成签名
        	    SortedMap<String,Object> parameters = new TreeMap<String,Object>();
        	    parameters.put("appid", appid);
        	    parameters.put("mch_id", mch_id);
        	    parameters.put("nonce_str", nonce_str);
        	    parameters.put("out_trade_no", out_trade_no);
        	    parameters.put("out_refund_no", out_refund_no);
        	    parameters.put("total_fee", total_fee);
        	    parameters.put("refund_fee", refund_fee);
        	    	
        	    // 生成签名
        	    String sign = WeChatUtils.createSign("UTF-8", parameters, apiKey);
        	    parameters.put("sign", sign);
        	    
        	    log.info("调用【微信（APP）-申请退款】接口的参数为：" + parameters.toString());
        	    
        	    // 构造xml参数
        	    String xmlInfo = WeChatUtils.getRequestXml(parameters);
        	    
        	    // 组装post，调用微信接口
        	    HttpPost httppost = new HttpPost("https://api.mch.weixin.qq.com/secapi/pay/refund"); 
        	    httppost.addHeader("Connection", "keep-alive");  
                httppost.addHeader("Accept", "*/*");  
                httppost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");  
                httppost.addHeader("Host", "api.mch.weixin.qq.com");  
                httppost.addHeader("X-Requested-With", "XMLHttpRequest");  
                httppost.addHeader("Cache-Control", "max-age=0");  
                httppost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");  
        	    httppost.setEntity(new StringEntity(xmlInfo,"UTF-8"));
        	    
        	    CloseableHttpResponse response = httpclient.execute(httppost);
        	    
        	    // 微信返回值处理
                try {  
                    HttpEntity entity = response.getEntity();
                    
                    if (entity == null) {
                    	log.info("调用【微信（APP）-申请退款】接口的结果【entity】为null =》 退款失败");
                    	return "FAIL";
                    }
                    
                    String xmlResult = EntityUtils.toString(entity,"UTF-8");
                    
                    log.info("调用【微信（APP）-申请退款】接口的结果字符串为：" + xmlResult);
                    
                    // 释放连接资源
                    EntityUtils.consume(entity);
                    
                    if (StringUtils.isEmpty(xmlResult)) {
                    	log.info("调用【微信（APP）-申请退款】接口的结果【xmlResult】为null =》 退款失败");
                    	return "FAIL";
                    }
                    
                    Map resultMap = HttpXmlUtils.doXMLParse(xmlResult);
                    log.info("调用【微信（APP）-申请退款】接口的结果map为：" + xmlResult);

                    String return_code = resultMap.get("return_code").toString();
                    if("FAIL".equals(return_code)){
                    	log.info("调用【微信（APP）-申请退款】接口的结果【return_code】为FAIL =》 退款失败");
                    	log.info("调用【微信（APP）-申请退款】接口的结果【return_msg】为："+resultMap.get("return_msg").toString());
                    	return "FAIL";
                    }
                    
                    String result_code = resultMap.get("result_code").toString();
                    if("FAIL".equals(result_code)){
                    	log.info("调用【微信（APP）-申请退款】接口的结果【result_code】为FAIL =》 退款失败");
                    	log.info("调用【微信（APP）-申请退款】接口的结果【err_code】为："+resultMap.get("err_code").toString());
                    	log.info("调用【微信（APP）-申请退款】接口的结果【err_code_des】为："+resultMap.get("err_code_des").toString());
                    	return "FAIL";
                    }

                } finally {
                    response.close();  
                }  
            } finally {  
                httpclient.close();  
            }
            return "SUCCESS";  
      }
      
      /**
       * 微信（小程序）-申请退款
       * @param 订单编号
       * @param 支付价格
       */
      public static String wechatRefund_js(String orderCode, String payPrice) throws Exception {  
            
    	  	String mch_id = PropertiesUtils.getInstance().getValue("mch_id_js");	// 微信支付分配的商户号（小程序）
    	  	
    	  	// 调用证书
    	  	KeyStore keyStore  = KeyStore.getInstance("PKCS12");  
            StringBuilder res = new StringBuilder("");  
            FileInputStream instream = new FileInputStream(new File(PropertiesUtils.getInstance().getValue("apiclient_js")));  
            try {  
                keyStore.load(instream, mch_id.toCharArray());
            } finally {  
                instream.close();
            }  
  
            // Trust own CA and all self-signed certs  
            SSLContext sslcontext = SSLContexts.custom()
                    .loadKeyMaterial(keyStore, mch_id.toCharArray())  
                    .build();
            // Allow TLSv1 protocol only  
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(  
            		sslcontext,
                    new String[] { "TLSv1" },
                    null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);  
            CloseableHttpClient httpclient = HttpClients.custom()  
                    .setSSLSocketFactory(sslsf)  
                    .build();
            
            // 整理参数，调用微信接口
            try {  
                // 整理参数
                String appid = PropertiesUtils.getInstance().getValue("appid_js");		// 微信开放平台审核通过的应用APPID（小程序）
                String apiKey = PropertiesUtils.getInstance().getValue("api_key_js");	// api秘钥（小程序）
        	    String nonce_str = WeChatUtils.getRandomString(32);						// 32位的随机数
        	    String out_trade_no = orderCode;										// 订单编号
        	    String out_refund_no = orderCode;										// 商户系统内部的退款单号
        	    int total_fee = WeChatUtils.getCentsByYuan(payPrice);					// 订单金额（分）
        	    int refund_fee = total_fee;												// 退款金额（分）
        	    
        	    // 组装参数并生成签名
        	    SortedMap<String,Object> parameters = new TreeMap<String,Object>();
        	    parameters.put("appid", appid);
        	    parameters.put("mch_id", mch_id);
        	    parameters.put("nonce_str", nonce_str);
        	    parameters.put("out_trade_no", out_trade_no);
        	    parameters.put("out_refund_no", out_refund_no);
        	    parameters.put("total_fee", total_fee);
        	    parameters.put("refund_fee", refund_fee);
        	    parameters.put("op_user_id", mch_id);	//操作员账号，默认是商户号
        	    	
        	    // 生成签名
        	    String sign = WeChatUtils.createSign("UTF-8", parameters, apiKey);
        	    parameters.put("sign", sign);
        	    
        	    log.info("调用【微信（小程序）-申请退款】接口的参数为：" + parameters.toString());
        	    
        	    // 构造xml参数
        	    String xmlInfo = WeChatUtils.getRequestXml(parameters);
        	    
        	    // 组装post，调用微信接口
        	    HttpPost httppost = new HttpPost("https://api.mch.weixin.qq.com/secapi/pay/refund"); 
        	    httppost.addHeader("Connection", "keep-alive");  
                httppost.addHeader("Accept", "*/*");  
                httppost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");  
                httppost.addHeader("Host", "api.mch.weixin.qq.com");  
                httppost.addHeader("X-Requested-With", "XMLHttpRequest");  
                httppost.addHeader("Cache-Control", "max-age=0");  
                httppost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");  
        	    httppost.setEntity(new StringEntity(xmlInfo,"UTF-8"));
        	    
        	    CloseableHttpResponse response = httpclient.execute(httppost);
        	    
        	    // 微信返回值处理
                try {  
                    HttpEntity entity = response.getEntity();
                    
                    if (entity == null) {
                    	log.info("调用【微信（小程序）-申请退款】接口的结果【entity】为null =》 退款失败");
                    	return "FAIL";
                    }
                    
                    String xmlResult = EntityUtils.toString(entity,"UTF-8");
                    
                    log.info("调用【微信（小程序）-申请退款】接口的结果字符串为：" + xmlResult);
                    
                    // 释放连接资源
                    EntityUtils.consume(entity);
                    
                    if (StringUtils.isEmpty(xmlResult)) {
                    	log.info("调用【微信（小程序）-申请退款】接口的结果【xmlResult】为null =》 退款失败");
                    	return "FAIL";
                    }
                    
                    Map resultMap = HttpXmlUtils.doXMLParse(xmlResult);
                    log.info("调用【微信（小程序）-申请退款】接口的结果map为：" + xmlResult);

                    String return_code = resultMap.get("return_code").toString();
                    if("FAIL".equals(return_code)){
                    	log.info("调用【微信（小程序）-申请退款】接口的结果【return_code】为FAIL =》 退款失败");
                    	log.info("调用【微信（小程序）-申请退款】接口的结果【return_msg】为："+resultMap.get("return_msg").toString());
                    	return "FAIL";
                    }
                    
                    String result_code = resultMap.get("result_code").toString();
                    if("FAIL".equals(result_code)){
                    	log.info("调用【微信（小程序）-申请退款】接口的结果【result_code】为FAIL =》 退款失败");
                    	log.info("调用【微信（小程序）-申请退款】接口的结果【err_code】为："+resultMap.get("err_code").toString());
                    	log.info("调用【微信（小程序）-申请退款】接口的结果【err_code_des】为："+resultMap.get("err_code_des").toString());
                    	return "FAIL";
                    }

                } finally { 
                    response.close();  
                }  
            } finally {  
                httpclient.close();  
            }
            return "SUCCESS";  
      }
      
      /**
       * 微信（小程序）-申请退款 - PC端
       * @param 订单编号
       * @param 支付价格
       */
      public static String wechatRefund_js_pc(String orderCode, String payPrice, String backPrice) throws Exception {  
            
    	  	String mch_id = PropertiesUtils.getInstance().getValue("mch_id_js");	// 微信支付分配的商户号（小程序）
    	  	
    	  	// 调用证书
    	  	KeyStore keyStore  = KeyStore.getInstance("PKCS12");  
            StringBuilder res = new StringBuilder("");  
            FileInputStream instream = new FileInputStream(new File(PropertiesUtils.getInstance().getValue("apiclient_js")));  
            try {  
                keyStore.load(instream, mch_id.toCharArray());
            } finally {  
                instream.close();
            }  
  
            // Trust own CA and all self-signed certs  
            SSLContext sslcontext = SSLContexts.custom()
                    .loadKeyMaterial(keyStore, mch_id.toCharArray())  
                    .build();
            // Allow TLSv1 protocol only  
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(  
            		sslcontext,
                    new String[] { "TLSv1" },
                    null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);  
            CloseableHttpClient httpclient = HttpClients.custom()  
                    .setSSLSocketFactory(sslsf)  
                    .build();
            
            // 整理参数，调用微信接口
            try {  
                // 整理参数
                String appid = PropertiesUtils.getInstance().getValue("appid_js");		// 微信开放平台审核通过的应用APPID（小程序）
                String apiKey = PropertiesUtils.getInstance().getValue("api_key_js");	// api秘钥（小程序）
        	    String nonce_str = WeChatUtils.getRandomString(32);						// 32位的随机数
        	    String out_trade_no = orderCode;										// 订单编号
        	    String out_refund_no = orderCode;										// 商户系统内部的退款单号
        	    int total_fee = WeChatUtils.getCentsByYuan(payPrice);					// 订单金额（分）
        	    int refund_fee = WeChatUtils.getCentsByYuan(backPrice);					// 退款金额（分）
        	    
        	    // 组装参数并生成签名
        	    SortedMap<String,Object> parameters = new TreeMap<String,Object>();
        	    parameters.put("appid", appid);
        	    parameters.put("mch_id", mch_id);
        	    parameters.put("nonce_str", nonce_str);
        	    parameters.put("out_trade_no", out_trade_no);
        	    parameters.put("out_refund_no", out_refund_no);
        	    parameters.put("total_fee", total_fee);
        	    parameters.put("refund_fee", refund_fee);
        	    parameters.put("op_user_id", mch_id);	//操作员账号，默认是商户号
        	    	
        	    // 生成签名
        	    String sign = WeChatUtils.createSign("UTF-8", parameters, apiKey);
        	    parameters.put("sign", sign);
        	    
        	    log.info("调用【微信（小程序）-申请退款】接口的参数为：" + parameters.toString());
        	    
        	    // 构造xml参数
        	    String xmlInfo = WeChatUtils.getRequestXml(parameters);
        	    
        	    // 组装post，调用微信接口
        	    HttpPost httppost = new HttpPost("https://api.mch.weixin.qq.com/secapi/pay/refund"); 
        	    httppost.addHeader("Connection", "keep-alive");  
                httppost.addHeader("Accept", "*/*");  
                httppost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");  
                httppost.addHeader("Host", "api.mch.weixin.qq.com");  
                httppost.addHeader("X-Requested-With", "XMLHttpRequest");  
                httppost.addHeader("Cache-Control", "max-age=0");  
                httppost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");  
        	    httppost.setEntity(new StringEntity(xmlInfo,"UTF-8"));
        	    
        	    CloseableHttpResponse response = httpclient.execute(httppost);
        	    
        	    // 微信返回值处理
                try {  
                    HttpEntity entity = response.getEntity();
                    
                    if (entity == null) {
                    	log.info("调用【微信（小程序）-申请退款】接口的结果【entity】为null =》 退款失败");
                    	return "FAIL";
                    }
                    
                    String xmlResult = EntityUtils.toString(entity,"UTF-8");
                    
                    log.info("调用【微信（小程序）-申请退款】接口的结果字符串为：" + xmlResult);
                    
                    // 释放连接资源
                    EntityUtils.consume(entity);
                    
                    if (StringUtils.isEmpty(xmlResult)) {
                    	log.info("调用【微信（小程序）-申请退款】接口的结果【xmlResult】为null =》 退款失败");
                    	return "FAIL";
                    }
                    
                    Map resultMap = HttpXmlUtils.doXMLParse(xmlResult);
                    log.info("调用【微信（小程序）-申请退款】接口的结果map为：" + xmlResult);

                    String return_code = resultMap.get("return_code").toString();
                    if("FAIL".equals(return_code)){
                    	log.info("调用【微信（小程序）-申请退款】接口的结果【return_code】为FAIL =》 退款失败");
                    	log.info("调用【微信（小程序）-申请退款】接口的结果【return_msg】为："+resultMap.get("return_msg").toString());
                    	return "FAIL";
                    }
                    
                    String result_code = resultMap.get("result_code").toString();
                    if("FAIL".equals(result_code)){
                    	log.info("调用【微信（小程序）-申请退款】接口的结果【result_code】为FAIL =》 退款失败");
                    	log.info("调用【微信（小程序）-申请退款】接口的结果【err_code】为："+resultMap.get("err_code").toString());
                    	log.info("调用【微信（小程序）-申请退款】接口的结果【err_code_des】为："+resultMap.get("err_code_des").toString());
                    	return "FAIL";
                    }

                } finally { 
                    response.close();  
                }  
            } finally {  
                httpclient.close();  
            }
            return "SUCCESS";  
      }
      
      
      /**
       * 微信（APP）-申请退款-pc
       * @param 订单编号
       * @param 支付价格
       */
      public static String wechatRefund_pc(String orderCode, String payPrice, String backPrice) throws Exception {  
            
    	  	String mch_id = PropertiesUtils.getInstance().getValue("mch_id");	// 微信支付分配的商户号（APP）
    	  	
    	  	// 调用证书
    	  	KeyStore keyStore  = KeyStore.getInstance("PKCS12");  
            StringBuilder res = new StringBuilder("");  
            FileInputStream instream = new FileInputStream(new File(PropertiesUtils.getInstance().getValue("apiclient")));  
            try {  
                keyStore.load(instream, mch_id.toCharArray());
            } finally {  
                instream.close();
            }  
  
            // Trust own CA and all self-signed certs  
            SSLContext sslcontext = SSLContexts.custom()
                    .loadKeyMaterial(keyStore, mch_id.toCharArray())  
                    .build();
            // Allow TLSv1 protocol only  
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(  
            		sslcontext,
                    new String[] { "TLSv1" },
                    null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);  
            CloseableHttpClient httpclient = HttpClients.custom()  
                    .setSSLSocketFactory(sslsf)  
                    .build();
            
            // 整理参数，调用微信接口
            try {  
                // 整理参数
                String appid = PropertiesUtils.getInstance().getValue("appid");		// 微信开放平台审核通过的应用APPID（APP）
                String apiKey = PropertiesUtils.getInstance().getValue("api_key");	// api秘钥（APP）
        	    String nonce_str = WeChatUtils.getRandomString(32);					// 32位的随机数
        	    String out_trade_no = orderCode;									// 订单编号
        	    String out_refund_no = orderCode;									// 商户系统内部的退款单号
        	    int total_fee = WeChatUtils.getCentsByYuan(payPrice);				// 订单金额（分）
        	    int refund_fee = WeChatUtils.getCentsByYuan(backPrice);				// 退款金额（分）
        	    
        	    // 组装参数并生成签名
        	    SortedMap<String,Object> parameters = new TreeMap<String,Object>();
        	    parameters.put("appid", appid);
        	    parameters.put("mch_id", mch_id);
        	    parameters.put("nonce_str", nonce_str);
        	    parameters.put("out_trade_no", out_trade_no);
        	    parameters.put("out_refund_no", out_refund_no);
        	    parameters.put("total_fee", total_fee);
        	    parameters.put("refund_fee", refund_fee);
        	    	
        	    // 生成签名
        	    String sign = WeChatUtils.createSign("UTF-8", parameters, apiKey);
        	    parameters.put("sign", sign);
        	    
        	    log.info("调用【微信（APP）-申请退款】接口的参数为：" + parameters.toString());
        	    
        	    // 构造xml参数
        	    String xmlInfo = WeChatUtils.getRequestXml(parameters);
        	    
        	    // 组装post，调用微信接口
        	    HttpPost httppost = new HttpPost("https://api.mch.weixin.qq.com/secapi/pay/refund"); 
        	    httppost.addHeader("Connection", "keep-alive");  
                httppost.addHeader("Accept", "*/*");  
                httppost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");  
                httppost.addHeader("Host", "api.mch.weixin.qq.com");  
                httppost.addHeader("X-Requested-With", "XMLHttpRequest");  
                httppost.addHeader("Cache-Control", "max-age=0");  
                httppost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");  
        	    httppost.setEntity(new StringEntity(xmlInfo,"UTF-8"));
        	    
        	    CloseableHttpResponse response = httpclient.execute(httppost);
        	    
        	    // 微信返回值处理
                try {  
                    HttpEntity entity = response.getEntity();
                    
                    if (entity == null) {
                    	log.info("调用【微信（APP）-申请退款】接口的结果【entity】为null =》 退款失败");
                    	return "FAIL";
                    }
                    
                    String xmlResult = EntityUtils.toString(entity,"UTF-8");
                    
                    log.info("调用【微信（APP）-申请退款】接口的结果字符串为：" + xmlResult);
                    
                    // 释放连接资源
                    EntityUtils.consume(entity);
                    
                    if (StringUtils.isEmpty(xmlResult)) {
                    	log.info("调用【微信（APP）-申请退款】接口的结果【xmlResult】为null =》 退款失败");
                    	return "FAIL";
                    }
                    
                    Map resultMap = HttpXmlUtils.doXMLParse(xmlResult);
                    log.info("调用【微信（APP）-申请退款】接口的结果map为：" + xmlResult);

                    String return_code = resultMap.get("return_code").toString();
                    if("FAIL".equals(return_code)){
                    	log.info("调用【微信（APP）-申请退款】接口的结果【return_code】为FAIL =》 退款失败");
                    	log.info("调用【微信（APP）-申请退款】接口的结果【return_msg】为："+resultMap.get("return_msg").toString());
                    	return "FAIL";
                    }
                    
                    String result_code = resultMap.get("result_code").toString();
                    if("FAIL".equals(result_code)){
                    	log.info("调用【微信（APP）-申请退款】接口的结果【result_code】为FAIL =》 退款失败");
                    	log.info("调用【微信（APP）-申请退款】接口的结果【err_code】为："+resultMap.get("err_code").toString());
                    	log.info("调用【微信（APP）-申请退款】接口的结果【err_code_des】为："+resultMap.get("err_code_des").toString());
                    	return "FAIL";
                    }

                } finally {
                    response.close();  
                }  
            } finally {  
                httpclient.close();  
            }
            return "SUCCESS";  
      }
}
