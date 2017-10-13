package com.liketry.interaction.opt.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
 
/**
 * post提交xml格式的参数
 * 
 * @author wuke
 */
public class HttpXmlUtils {
 
  /**
   * 开始post提交参数到接口,并接受返回
   * @param url
   * @param xml
   * @param method
   * @param contentType
   * @return
   */
  public static String xmlHttpProxy(String url,String xml,String method,String contentType){
    InputStream is = null;
    OutputStreamWriter os = null;
 
    try {
      URL _url = new URL(url);
      HttpURLConnection conn = (HttpURLConnection) _url.openConnection();
      conn.setDoInput(true);  
      conn.setDoOutput(true);  
      conn.setRequestProperty("Content-type", "text/xml");
      conn.setRequestProperty("Pragma:", "no-cache"); 
      conn.setRequestProperty("Cache-Control", "no-cache"); 
      conn.setRequestMethod("POST");
      os = new OutputStreamWriter(conn.getOutputStream());
      os.write(new String(xml.getBytes(contentType)));
      os.flush();
 
      //返回值
      is = conn.getInputStream();
      return getContent(is, "utf-8");
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally{
      try {
        if(os!=null){os.close();}
        if(is!=null){is.close();}
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return null;
  }
 
  /**
   * 解析返回的值
   * @param is
   * @param charset
   * @return
   */
  public static String getContent(InputStream is, String charset) {
    String pageString = null;
    InputStreamReader isr = null;
    BufferedReader br = null;
    StringBuffer sb = null;
    try {
      isr = new InputStreamReader(is, charset);
      br = new BufferedReader(isr);
      sb = new StringBuffer();
      String line = null;
      while ((line = br.readLine()) != null) {
        sb.append(line + "\n");
      }
      pageString = sb.toString();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (is != null){
          is.close();
        }
        if(isr!=null){
          isr.close();
        }
        if(br!=null){
          br.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      sb = null;
    }
    return pageString;
  }
 
  /**
   * post请求并得到返回结果
   * @param requestUrl
   * @param requestMethod
   * @param output
   * @return
   */
  public static String httpsRequest(String requestUrl, String requestMethod, String output) {
    try{
      URL url = new URL(requestUrl);
      HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
      connection.setDoOutput(true);
      connection.setDoInput(true);
      connection.setUseCaches(false);
      connection.setRequestMethod(requestMethod);
      if (null != output) {
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(output.getBytes("UTF-8"));
        outputStream.close();
      }
      // 从输入流读取返回内容
      InputStream inputStream = connection.getInputStream();
      InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
      BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
      String str = null;
      StringBuffer buffer = new StringBuffer();
      while ((str = bufferedReader.readLine()) != null) {
        buffer.append(str);
      }
      bufferedReader.close();
      inputStreamReader.close();
      inputStream.close();
      inputStream = null;
      connection.disconnect();
      return buffer.toString();
    }catch(Exception ex){
      ex.printStackTrace();
    }
 
    return "";
  }
  
  /**
   * 将返回的xml格式的request输入流，转换成map
   * @param request
   * @return
   * @throws JDOMException
   * @throws IOException
   */
  public static Map<String, String> getMapByRequest(HttpServletRequest request) throws JDOMException, IOException {
	  
	  InputStream inStream = request.getInputStream();
	  ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
	  
	  byte[] buffer = new byte[1024];
	  int len = 0;
	  
	  while ((len = inStream.read(buffer)) != -1) {
		  outSteam.write(buffer, 0, len);
	  }
	  
	  String resultxml = new String(outSteam.toByteArray(), "utf-8");
	  Map<String, String> map = doXMLParse(resultxml);
	  
	  outSteam.close();
	  inStream.close();
	  
	  return map;
  }

//  public static Map parseXmlToMap(String xml) {
//	  //  Map retMap = new HashMap();
//	    SortedMap<String, String> retMap = new TreeMap<String, String>();
//	    try {
//	        StringReader read = new StringReader(xml);
//	        // 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
//	        InputSource source = new InputSource(read);
//	        // 创建一个新的SAXBuilder
//	        SAXBuilder sb = new SAXBuilder();
//	        // 通过输入源构造一个Document
//	        Document doc =  sb.build(source);
//	        Element root = (Element) doc.getRootElement();// 指向根节点
//	        List<Element> es = root.getChildren();
//	        if (es != null && es.size() != 0) {
//	            for (Element element : es) {
//	                retMap.put(element.getName(), element.getValue());
//	            }
//	        }
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	    }
//	    return retMap;
//	}
  
  /**
   * xml解析
   * @param strxml
   * @return
   * @throws JDOMException
   * @throws IOException
   */
  public static Map doXMLParse(String strxml) throws JDOMException, IOException {  
        strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");  

        if(null == strxml || "".equals(strxml)) {  
            return null;  
        }  
          
        Map m = new HashMap();  
          
        InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));  
        SAXBuilder builder = new SAXBuilder();  
        Document doc = builder.build(in);  
        Element root = doc.getRootElement();  
        List list = root.getChildren();  
        Iterator it = list.iterator();  
        while(it.hasNext()) {  
            Element e = (Element) it.next();  
            String k = e.getName();  
            String v = "";  
            List children = e.getChildren();  
            if(children.isEmpty()) {  
                v = e.getTextNormalize();  
            } else {  
                v = getChildrenText(children);  
            }  
              
            m.put(k, v);  
        }  
          
        //关闭流  
        in.close();  
          
        return m;  
  }  
  
  public static String getChildrenText(List children) {  
        StringBuffer sb = new StringBuffer();  
        if(!children.isEmpty()) {  
            Iterator it = children.iterator();  
            while(it.hasNext()) {  
                Element e = (Element) it.next();  
                String name = e.getName();  
                String value = e.getTextNormalize();  
                List list = e.getChildren();  
                sb.append("<" + name + ">");  
                if(!list.isEmpty()) {  
                    sb.append(getChildrenText(list));  
                }  
                sb.append(value);  
                sb.append("</" + name + ">");  
            }  
        }  
          
        return sb.toString();  
  }

 
}
