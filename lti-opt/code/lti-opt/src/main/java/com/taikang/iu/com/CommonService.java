package com.taikang.iu.com;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.slf4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.taikang.udp.framework.common.util.logger.LoggerFactory;

/**
 * 共通接口
 * @author t-wuke
 * @version [版本号，默认V1.0.0]
 * @Credited 2015年6月5日 下午10:30:05
 */
public class CommonService {
	
	public static final Logger logger = LoggerFactory.getLogger();
	
	/**
     *  获取编号序列值（通过sev接口）
     */
    public static String getBusinessSeqNextValue(String code) {
        
    	logger.debug("<====== Start CommonService.getBusinessSeqNextValue ======>");
		
    	String result = null;
    	
    	// 调用接口取值
		WebClient client = ServiceUtil.createClient("/commonService/BusinessSeqNextValue/" + code);
        client.type("application/json;charset=UTF-8");
        Response response = client.get();
        
        // 转换返回值为jsonNode
        JsonNode node = ServiceUtil.convertJsonNode(response);
        
        // 为返回值赋值
        result = (node.get("nextValue") != null ? node.get("nextValue").asText() : "");
        
        logger.debug("<====== End CommonService.getBusinessSeqNextValue ======>");
        
        return result;
    }
    
    /**
     *  获取普通序列值（通过sev接口）
     */
    public static String getSeqNextValue(String code) {
        
    	logger.debug("<====== Start CommonService.getSeqNextValue ======>");
		
    	String result = null;
    	
    	// 调用接口取值
		WebClient client = ServiceUtil.createClient("/commonService/SeqNextValue/" + code);
        client.type("application/json;charset=UTF-8");
        Response response = client.get();
        
        // 转换返回值为jsonNode
        JsonNode node = ServiceUtil.convertJsonNode(response);
        
        // 为返回值赋值
        result = (node.get("nextValue") != null ? node.get("nextValue").asText() : "");
        
        logger.debug("<====== End CommonService.getSeqNextValue ======>");
        
        return result;
    }
}
