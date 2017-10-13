package com.taikang.iu.com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.common.util.Base64Utility;
import org.apache.cxf.jaxrs.client.WebClient;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.common.json.JsonMapper;
import com.taikang.udp.framework.common.util.TKConstants;
import com.taikang.udp.framework.common.util.TKDateTimeUtils;
import com.taikang.udp.framework.core.exception.TKCheckedException;
import com.taikang.udp.framework.core.properties.PropertiesFile;
import com.taikang.udp.framework.core.properties.PropertiesHandler;
import com.taikang.udp.framework.core.properties.PropertiesHandlerFactory;

/**
 * 客户端接口相关功能
 * @author t-wuke
 * @version [版本号，默认V1.0.0]
 * @Credited 2015年3月17日 下午5:43:48
 */
public class ServiceUtil {
	
	protected static PropertiesHandler initProperty = PropertiesHandlerFactory.getPropertiesHelper(PropertiesFile.FRAMEWORK);
	
//	private static String BASE_URI = "http://localhost:8090/sev/service";
	
	private static String BASE_URI = initProperty.getValue("service.baseUrl");
    
    private static JsonMapper objectMapper = new JsonMapper();

    private static boolean formateOutputJson = true;
	
    /**
     *  创建webclient
     */
    public static WebClient createClient(String uri) {
        WebClient client = WebClient.create(BASE_URI + uri);
        String auth = "Basic " + Base64Utility.encode("kermit:kermit".getBytes());
        client.header("Authorization", auth);
        return client;
    }
    
    /**
     * 生成body
     */
    public static String convertBody(Object one){
    	if(one == null){
    		return null;
    	}
    	
    	String body = null;
    	try {
    		body = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(one);
		} catch (JsonProcessingException e) {
			System.out.println("convertBody 转换时发生错误, 转换对象数据为： " + one.toString());
		}
    	return body;
    }
    
    /**
     *  打印输出结果，并生成JsonNode
     */
    public static JsonNode convertJsonNode(Response response) {
        try {
            InputStream stream = (InputStream) response.getEntity();
            int available = 0;
            available = stream.available();

            if (available == 0) {
                System.out.println("nothing returned, response code: " + response.getStatus());
                return null;
            }
            JsonNode responseNode = objectMapper.readTree(stream);
            if (formateOutputJson) {
                System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseNode));
            } else {
                System.out.println(objectMapper.writeValueAsString(responseNode));
            }
            return responseNode;
        } catch (IOException e) {
            System.err.println("catch an exception: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     *  Timestamp转换（从长整形转换成timestamp）
     */
    public static Timestamp convertTimestamp(String longStr){
    	if(longStr == null){
    		return null;
    	}
    	
        Date date = new Date(Long.parseLong(longStr));
        String dateStr = TKDateTimeUtils.formatDate(date, TKConstants.DATE_TIME_FORMAT);
        return TKDateTimeUtils.convertToTimestamp(dateStr);
    }
    
    /**
     *  response返回值转换成XML字符串，并打印
     * @throws IOException 
     */
    public static String responseToXML(Response response) throws IOException{
    	if(response == null){
    		return null;
    	}
    	
    	StringBuffer str = new StringBuffer();
		InputStreamReader read = null;
		BufferedReader bufferedReader = null;
		
		read = new InputStreamReader((InputStream) response.getEntity(), "UTF-8");// 考虑到编码格式
		bufferedReader = new BufferedReader(read);
		String lineTxt = null;
		while ((lineTxt = bufferedReader.readLine()) != null) {
			str.append(lineTxt.trim());
		}
		bufferedReader.close();
		read.close();
		
    	if(str == null){
    		System.out.println("***********返回的字符串为NULL！***********");
    		return null;
    	}
		
		System.out.println("****************************************");
		System.out.println(str.toString());
		System.out.println("****************************************");
		
		return str.toString();
    }
    
    /**
	 * 递归MAP转换为xml
	 */
	public static String recurParseDto2XmlBasedNode(String root, Dto dto) throws TKCheckedException{
		if (StringUtils.isEmpty(root)) {
			throw new TKCheckedException("root参数不能为空");
		}

		if (dto == null || dto.isEmpty()) {
			return "";
		}

		String result = "";
		Document document = DocumentHelper.createDocument();
		Element rootEle = new DefaultElement(root);

		if (dto != null && !dto.isEmpty()) {
			Element handledRootEle = recurParseDto2ElementBasedNode(rootEle, dto);
			if (handledRootEle != null) {
				document.setRootElement(handledRootEle);
			}
		} else {
			document.setRootElement(rootEle);
		}

		result = document.asXML();
		return result;
	}
	
	// 递归转换xml为MAP
	private static Element recurParseDto2ElementBasedNode(Element element, Dto dto) {
		if (element == null) {
			return element;
		}

		if (dto != null && !dto.isEmpty()) {
			Set<Entry> entrySet = dto.entrySet();
			for (Entry entry : entrySet) {
				// map中的key和value都可以是空
				Object keyObject = entry.getKey();
				Object valueObject = entry.getValue();
				
				if (keyObject == null) {
					continue;
				}
				
				String key = keyObject.toString();
				Object value = entry.getValue();

				if (value instanceof String) {
					Element e1 = new DefaultElement(key);
					e1.setText((String) value);
					element.add(e1);
				} else if (value instanceof List) {
					Element e1 = new DefaultElement(key);
					List<Dto> dtoList = (List<Dto>) value;
					for (Dto oneDto : dtoList) {
						recurParseDto2ElementBasedNode(e1, oneDto);
					}
					element.add(e1);
				} else if (value instanceof Dto) {
					Element e1 = new DefaultElement(key);
					recurParseDto2ElementBasedNode(e1, (Dto) value);
					element.add(e1);
				} else {
					Element e1 = new DefaultElement(key);
					e1.setText(value == null ? "" : value.toString());
					element.add(e1);
				}
			}
		}
		return element;
	}

}
