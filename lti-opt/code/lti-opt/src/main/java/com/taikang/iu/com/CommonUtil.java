package com.taikang.iu.com;

import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.adobe.xmp.impl.Base64;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import com.taikang.udp.framework.core.properties.PropertiesFile;
import com.taikang.udp.framework.core.properties.PropertiesHandler;
import com.taikang.udp.framework.core.properties.PropertiesHandlerFactory;

import sun.misc.BASE64Encoder;

/**
 * 工具类
 * @author t-wuke
 * @version [版本号，默认V1.0.0]
 * @Credited 2015年4月17日 下午5:43:48
 */
public class CommonUtil {
	
	protected static PropertiesHandler initProperty = PropertiesHandlerFactory.getPropertiesHelper(PropertiesFile.FRAMEWORK);
	
	public static String RELATION_UPLOAD_FILEPATH = initProperty.getValue("relation_upload_filepath");
	
	private static String decryptKey = "likebenisontry";//加密KEY
	
	private static final Log log = LogFactory.getLog(CommonUtil.class);
    
	public static String uploadFilePath(){
		String defaultFilePath = "";
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null) {
			classLoader = PropertiesHandlerFactory.class.getClassLoader();
		}
		// 加载属性文件global.eredbos.properties
		try {
			InputStream is = classLoader.getResourceAsStream("META-INF/app_config/properties/init.config.properties");
			if(is!=null){
				PropertiesHandler ph = new PropertiesHandler(is);
				defaultFilePath = ph.getValue("default_upload_filepath");
			}			
		} catch (Exception e1) {
		}
		return defaultFilePath;
	}
	
	public static String exportUploadFilePath(){
		String defaultFilePath = "";
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null) {
			classLoader = PropertiesHandlerFactory.class.getClassLoader();
		}
		// 加载属性文件global.eredbos.properties
		try {
			InputStream is = classLoader.getResourceAsStream("META-INF/app_config/properties/init.config.properties");
			if(is!=null){
				PropertiesHandler ph = new PropertiesHandler(is);
				defaultFilePath = ph.getValue("exportImg_upload_filepath");
			}			
		} catch (Exception e1) {
		}
		return defaultFilePath;
	}


	/**
	 * 校验字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		 return str == null || "".equals(str.trim());
	}
	
	/**
	 * 自定义base64加密
	 * @param str
	 * @return
	 */
    public static String getBase64(String data){  
    	
        String res = null;  
        
        if(!StringUtils.isEmpty(data)){
        	log.info("<====退单入参json:=="+data+"=====>");
        	res = Base64.encode(decryptKey+Base64.encode(data));
        }
        
        return res;  
    } 
    
}