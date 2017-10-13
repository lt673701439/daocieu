package com.liketry.interaction.benison.api;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.liketry.interaction.benison.constants.SystemConstants;
import com.liketry.interaction.benison.sdk.Result;
import com.liketry.interaction.benison.service.OrderService;
import com.liketry.interaction.benison.util.HttpXmlUtils;
import com.liketry.interaction.benison.util.PropertiesUtils;
import com.liketry.interaction.benison.util.UserUtils;
import com.liketry.interaction.benison.util.WeChatUtils;

/**
 * 微信api
 *
 * @author wuke
 */
@RestController
@RequestMapping("wechat_api")
public class WeChatApiController {
	
	private static final Logger log = LoggerFactory.getLogger(WeChatApiController.class);
	
    @Autowired
    OrderService orderService;
	
	/**
	 * 微信（APP）-统一下单
	 * @param request
	 * @param appid
	 * @param body
	 * @param out_trade_no
	 * @param total_price
	 * @param spbill_create_ip
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/payorder", produces = "application/json;charset=UTF-8",method={RequestMethod.POST})
	public Result<SortedMap<String,Object>> wechatPay(HttpServletRequest request,@RequestBody String json) throws Exception { 

		log.info("<========wechatPay.json:=="+json+"=========>");
		
		if(json == null){
			return new Result<>(SystemConstants.RESULT_FALSE,("支付失败,传递的数据为空！"),null);
    	}
		
		JSONObject data = JSONObject.parseObject(json);

		String body = data.getString("body"); 							// 商品描述
		String attach = data.getString("attach"); 						// 订单ID
		String out_trade_no = data.getString("out_trade_no"); 			// 订单编号
		String total_price = data.getString("total_price"); 			// 订单总金额
		String spbill_create_ip = data.getString("spbill_create_ip"); 	// 用户端实际ip
		
		// 微信开放平台审核通过的应用APPID
		String appid = PropertiesUtils.getInstance().getValue("appid");		
		
		// 微信支付分配的商户号
		String mch_id = PropertiesUtils.getInstance().getValue("mch_id");
		
		// api秘钥（APP）
		String api_key = PropertiesUtils.getInstance().getValue("api_key");
		
		// 32位的随机数
	    String nonce_str = WeChatUtils.getRandomString(32);

	    // 订单总金额，单位为分
	    int total_fee = WeChatUtils.getCentsByYuan(total_price);
	    
	    // 接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
	    String notify_url = PropertiesUtils.getInstance().getValue("notify_url");
	    
	    // 支付类型
	    String trade_type = "APP";
	 
	    // 组装参数并生成签名
	    SortedMap<String,Object> parameters = new TreeMap<String,Object>();
	    parameters.put("appid", appid);
	    parameters.put("mch_id", mch_id);
	    parameters.put("nonce_str", nonce_str);
	    parameters.put("body", body);
	    parameters.put("attach", attach);	// 订单ID
	    parameters.put("out_trade_no", out_trade_no);
	    parameters.put("total_fee", total_fee);
	    parameters.put("spbill_create_ip", spbill_create_ip);
	    parameters.put("notify_url", notify_url);
	    parameters.put("trade_type", trade_type);
	    	
	    // 生成签名
	    String sign = WeChatUtils.createSign("UTF-8", parameters, api_key);
	    parameters.put("sign", sign);

	    
	    log.info("调用【微信（APP）-统一下单】接口的参数为：" + parameters.toString());
	    
	    // 构造xml参数
	    String xmlInfo = WeChatUtils.getRequestXml(parameters);
	 
	    String wxUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	 
	    String method = "POST";
	 
	    String wxRsult = HttpXmlUtils.httpsRequest(wxUrl, method, xmlInfo).toString();
	 
	    log.info("调用【微信（APP）-统一下单】接口的结果为：" + wxRsult);
	 
	    Map<String, String> resultMap = null;  
		try {  
			resultMap = HttpXmlUtils.doXMLParse(wxRsult);  
		} catch (JDOMException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
	    
		// 判断支付是否失败（分三级）
	    if(resultMap == null){
	    	return new Result<>(SystemConstants.RESULT_FALSE,"支付失败",null);
	    }else if("FAIL".equals(resultMap.get("return_code"))){
	    	return new Result<>(SystemConstants.RESULT_FALSE,(resultMap.get("return_msg") != null? resultMap.get("return_msg") : "支付失败"),null);
	    }else if("FAIL".equals(resultMap.get("result_code"))){
	    	return new Result<>(SystemConstants.RESULT_FALSE,(resultMap.get("err_code_des") != null? resultMap.get("err_code_des") : "支付失败"),null);
	    }
	    	
    	// 返回参数组装
    	SortedMap<String,Object> result = new TreeMap<String,Object>();
    	result.put("appid", appid);								// 微信开放平台审核通过的应用APPID
    	result.put("partnerid", mch_id);						// 微信支付分配的商户号
    	result.put("prepayid", resultMap.get("prepay_id"));		// 预支付交易会话标识
    	result.put("package", "Sign=WXPay");					// 扩展字段
    	result.put("noncestr", nonce_str);						// 随机字符串
    	result.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000)); // 时间戳只要10位

    	String signnew = WeChatUtils.createSign("UTF-8", result, api_key);
    	result.put("sign", signnew);
    	
    	log.info("调用【微信（APP）-统一下单】接口返回前台的数据为：" + result.toString());
	 
	    return new Result<>(SystemConstants.RESULT_SUCCESS,"支付成功",result);
	 
	  }
	
	/**
	 * 微信（JSAPI）-统一下单
	 * @param request
	 * @param appid
	 * @param body
	 * @param out_trade_no
	 * @param total_price
	 * @param spbill_create_ip
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/payorder_js", produces = "application/json;charset=UTF-8",method={RequestMethod.POST})
	public Result<SortedMap<String,Object>> wechatPayJs(HttpServletRequest request,@RequestBody String json) throws Exception { 

		log.info("<========wechatPay.json:=="+json+"=========>");
		
		if(json == null){
			return new Result<>(SystemConstants.RESULT_FALSE,("支付失败,传递的数据为空！"),null);
    	}
		
		JSONObject data = JSONObject.parseObject(json);
		log.info(data.toJSONString());
		String body = data.getString("body"); 							// 商品描述
		String attach = data.getString("attach"); 						// 订单ID
		String out_trade_no = data.getString("out_trade_no"); 			// 订单编号
		String total_price = data.getString("total_price"); 			// 订单总金额
		String openid = data.getString("openid"); 						// 用户标识
		
		// 小程序ID（小程序）
		String appid = PropertiesUtils.getInstance().getValue("appid_js");		
		
		// 微信支付分配的商户号（小程序）
		String mch_id = PropertiesUtils.getInstance().getValue("mch_id_js");
		
		// api秘钥（小程序）
		String api_key = PropertiesUtils.getInstance().getValue("api_key_js");
		
		// 32位的随机数
	    String nonce_str = WeChatUtils.getRandomString(32);

	    // 订单总金额，单位为分
	    int total_fee = WeChatUtils.getCentsByYuan(total_price);
	    
	    // 用户端实际ip
	    String spbill_create_ip = request.getRemoteAddr();
	    
	    // 接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
	    String notify_url = PropertiesUtils.getInstance().getValue("notify_url");
	    
	    // 支付类型
	    String trade_type = "JSAPI";
	 
	    // 组装参数并生成签名
	    SortedMap<String,Object> parameters = new TreeMap<String,Object>();
	    parameters.put("appid", appid);
	    parameters.put("mch_id", mch_id);
	    parameters.put("nonce_str", nonce_str);
	    parameters.put("body", body);
	    parameters.put("attach", attach);	// 订单ID
	    parameters.put("out_trade_no", out_trade_no);
	    parameters.put("total_fee", total_fee);
	    parameters.put("spbill_create_ip", spbill_create_ip);
	    parameters.put("notify_url", notify_url);
	    parameters.put("trade_type", trade_type);
	    parameters.put("openid", openid);
	    	
	    // 生成签名
	    String sign = WeChatUtils.createSign("UTF-8", parameters, api_key);
	    parameters.put("sign", sign);

	    
	    log.info("调用【微信（JSAPI）-统一下单】接口的参数为：" + parameters.toString());
	    
	    // 构造xml参数
	    String xmlInfo = WeChatUtils.getRequestXml(parameters);
	 
	    String wxUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	 
	    String method = "POST";
	 
	    String wxRsult = HttpXmlUtils.httpsRequest(wxUrl, method, xmlInfo).toString();
	 
	    log.info("调用【微信（JSAPI）-统一下单】接口的结果为：" + wxRsult);
	 
	    Map<String, String> resultMap = null;  
		try {  
			resultMap = HttpXmlUtils.doXMLParse(wxRsult);  
		} catch (JDOMException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
	    
		// 判断支付是否失败（分三级）
	    if(resultMap == null){
	    	return new Result<>(SystemConstants.RESULT_FALSE,"支付失败",null);
	    }else if("FAIL".equals(resultMap.get("return_code"))){
	    	return new Result<>(SystemConstants.RESULT_FALSE,(resultMap.get("return_msg") != null? resultMap.get("return_msg") : "支付失败"),null);
	    }else if("FAIL".equals(resultMap.get("result_code"))){
	    	return new Result<>(SystemConstants.RESULT_FALSE,(resultMap.get("err_code_des") != null? resultMap.get("err_code_des") : "支付失败"),null);
	    }
	    	
    	// 返回参数组装
    	SortedMap<String,Object> result = new TreeMap<String,Object>();
    	result.put("appId", appid);													// 小程序ID
    	result.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000)); // 时间戳只要10位 【大写的S】
    	result.put("nonceStr", nonce_str);											// 随机字符串 【大写的S】
    	result.put("package", "prepay_id="+resultMap.get("prepay_id"));				// 扩展字段 【prepay_id=*】
    	result.put("signType", "MD5");												// 签名算法，暂支持 MD5

    	String signnew = WeChatUtils.createSign("UTF-8", result, api_key);
    	result.put("paySign", signnew);
    	
    	log.info("调用【微信（JSAPI）-统一下单】接口返回前台的数据为：" + result.toString());
	 
	    return new Result<>(SystemConstants.RESULT_SUCCESS,"支付成功",result);
	 
	  }
	
	

	/**
	 * 微信支付–回调通知业务处理
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/returnmsg", produces = "application/json;charset=UTF-8",method={RequestMethod.POST})
	public String returnmsg(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 将返回的xml格式的request输入流，转换成map
		Map<String,String> map = HttpXmlUtils.getMapByRequest(request);
	    log.info("【微信支付–回调通知业务处理】结果为：" + map.toString());

	    // 校验签名
	    if(!WeChatUtils.isFormerSign("UTF-8", map)){
	    	log.info("【微信支付–回调通知业务处理】返回的签名与数据不匹配，有可能被第三方篡改!!!");
	    	return "FAIL";
	    }
	    
	    // 判断是否支付成功
	    if(map.get("return_code").equals("SUCCESS")) {
	    	// 支付价格
	    	String total_fee = map.get("total_fee");
	    	double payPrice = Double.valueOf(total_fee) / 100;
	    	
	    	// 支付类型
	    	String payType = "";
	    	if("APP".equals(map.get("trade_type"))){
	    		payType = "APP";
	    	}else if("JSAPI".equals(map.get("trade_type"))){
	    		payType = "JS";
	    	}
	    	
	    	log.info("【微信支付–回调通知业务处理】更新订单的数据为：订单ID【" + map.get("attach").trim() + "】、交易单号【"+ map.get("transaction_id").trim() 
	    			+ "】、支付价格【"+ new BigDecimal(payPrice) + "】、支付类型【" + payType + "】");
	    	
	    	// 根据订单ID，更新订单
	    	orderService.updateOrderStatus(map.get("attach").trim(), "微信支付回调接口", map.get("transaction_id").trim(), new BigDecimal(payPrice), payType);
	    }
	 
	    // 如果支付失败，则无需修改订单状态，客户可以在客户端继续进行支付操作。
	    
	    return "SUCCESS";
	}
	
	/**
	 * 0元虚拟支付接口（直接修改订单相应信息）
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/zeroPay")
	public Result<String> zeroPay(String data) throws Exception {
		
		JSONObject json = UserUtils.decrypt2Str(data);
		
		if(json == null){
			return new Result<String>(SystemConstants.RESULT_FALSE,"数据不能为空");
		}
		
    	// 支付价格
    	String total_fee = json.getString("totol_fee");
    	String order_id = json.getString("order_id");
    	double payPrice = Double.valueOf(total_fee) / 100;
    	
    	// 支付类型
    	String payType = "";
    	if("APP".equals(json.getString("trade_type"))){
    		payType = "APP";
    	}else if("JSAPI".equals(json.getString("trade_type"))){
    		payType = "JS";
    	}
    	
    	log.info("0元虚拟支付,更新订单的数据为：订单ID【" + order_id + "】、"
    			+ "】、支付价格【"+ new BigDecimal(payPrice) + "】、支付类型【" + payType + "】");
    	
    	// 根据订单ID，更新订单
	    return new Result<String>(SystemConstants.RESULT_SUCCESS, 
	    		orderService.updateOrderStatus(order_id, "微信支付回调接口",null, new BigDecimal(payPrice), payType));
	}
	
}
