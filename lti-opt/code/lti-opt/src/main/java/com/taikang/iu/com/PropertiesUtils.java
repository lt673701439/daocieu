package com.taikang.iu.com;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesUtils
{
	private static Logger log = LoggerFactory.getLogger(PropertiesUtils.class);
	
	private static final String PRIVATE = "../../../../META-INF/app_config/properties/init.config.properties";
//	private static final String PRIVATE = "init.config.properties";
	
	private static PropertiesUtils instance = null;

	Properties properties = new Properties();

	private PropertiesUtils()
	{
		InputStream privatePro = this.getClass().getResourceAsStream(PRIVATE);
		
		try{
			properties.load(new InputStreamReader(privatePro, "UTF-8"));
		}
		catch (IOException e){
			//TODO...修改为记录日志
			log.info("私有属性文件加载失败");
		}
		catch (Exception e){
			//TODO...修改为记录日志
			log.info("私有属性文件不存在");
		}
	}

	public static synchronized PropertiesUtils getInstance(){
		
		if (instance == null){
			instance = new PropertiesUtils();
		}
		return instance;
	}

	public String getValue(String code){
		return getValue(code, new String[]{});
	}

	public String getValue(String code, String... params){
		Object obj = properties.get(code);
		if (obj == null){
			return null;
		}

		String strValue = obj.toString();
		for (int i = 0; i < params.length; i++){
			strValue = strValue.replace("{" + i + "}", params[i]);
		}

		return strValue;
	}
	
	public static void main (String[] args){
		System.out.println(PropertiesUtils.getInstance().getValue("return_order_signName"));
	}
}
