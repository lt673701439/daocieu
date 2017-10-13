package com.liketry.util;

import java.util.UUID;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户工具类
 * @author pengyy
 */

@Slf4j
public class CommonUtils {
	
	 private static Pattern patter = Pattern.compile("1[0-9]{10}");
	
	/**
	 * 请求参数解码
	 * @param data
	 * @return
	 */
	public static JSONObject parse2Json(String data){
		
		JSONObject res = null;
		 
		if(!StringUtils.isEmpty(data)){
			
			try {
				res = JSONObject.parseObject(data);
			} catch (Exception e) {
				log.info("<====CommonUtils.parse2Json==入参json解析错误:"+e+"======>");
			}
			
		}
		
		log.info("<====CommonUtils.parse2Json==入参json:"+res+"======>");
		
		return res;
	}
	
	//验证手机号基本格式
    public static boolean valudateMobile(String mobile) {
        return !(mobile == null || mobile.length() != 11) && patter.matcher(mobile).matches();
    }
	//生成id
	public static String getId(){
		return UUID.randomUUID().toString().replace("-","");
	}
}
