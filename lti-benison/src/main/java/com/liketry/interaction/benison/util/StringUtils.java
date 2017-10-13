package com.liketry.interaction.benison.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liketry.interaction.benison.api.ShareApiController;

/**
 * 字符串工具类
 */
public class StringUtils {
	
	private static final Logger log = LoggerFactory.getLogger(ShareApiController.class);
	
	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
    public static boolean isEmpty(String str) {
        return str == null || "".equals(str.trim());
    }
    
    /** 
     * 字符串转二进制 
     * @param str 要转换的字符串 
     * @return  转换后的二进制数组 
     */  
    public byte[] hex2byte(String str) { // 字符串转二进制  
        if (str == null)  
            return null;  
        str = str.trim();  
        int len = str.length();  
        if (len == 0 || len % 2 == 1)  
            return null;  
        byte[] b = new byte[len / 2];  
        try {  
            for (int i = 0; i < str.length(); i += 2) {  
                b[i / 2] = (byte) Integer  
                        .decode("0X" + str.substring(i, i + 2)).intValue();  
            }  
            return b;  
        } catch (Exception e) {  
            return null;  
        }  
    } 
    
    /** 
     * 二进制转字符串 
     * @param b 
     * @return 
     */  
    public static String byte2hex(byte[] b) // 二进制转字符串  
    {  
        StringBuffer sb = new StringBuffer();  
        String stmp = "";  
        for (int n = 0; n < b.length; n++) {  
            stmp = Integer.toHexString(b[n] & 0XFF);  
            if (stmp.length() == 1) {  
                sb.append("0" + stmp);  
            } else {  
                sb.append(stmp);  
            }  
  
        }  
        return sb.toString();  
    }
    
    /**
	 * 处理文字内容
	 * @param content
	 * @return
	 */
    public static String fixName (String content){
		
		String str = null;
		
		if(content.contains("（") && content.contains("）")){
			content = toDBC(content);
			log.info("<=====处理前内容======>",content);
			String newStr = content.substring(content.indexOf("("),content.indexOf(")")+1 );
			str = content.replace(newStr, "");
			log.info("<=====处理后内容======>",str);
		}else{
			str = content;
		}
		
		return str;
	}
	
	/**
	 * 转全角的函数(SBC case)
	 * @param input
	 * @return
	 */
	public static String toSBC(String input){ //半角转全角：
	    
		char[] c=input.toCharArray();
	      
		for(int i = 0;i<c.length;i++){
			
	       if (c[i]==32){
	    	   c[i]=(char)12288; continue;
		   }
	       
		   if (c[i]<127){
			   c[i]=(char)(c[i]+65248);
		   }
		   
	   }
		
	   return new String(c);
	}
		  
	/**
	 * 转半角的函数(DBC case)
	 * @param input
	 * @return
	 */
	public static String toDBC(String input){
		
		char[] c=input.toCharArray();
		
		for(int i = 0;i<c.length;i++){
			
			if (c[i]==12288){
				c[i]=(char)32; continue;
			}
			
			if (c[i]>65280 && c[i]<65375){
				c[i]=(char)(c[i]-65248);
			}
				
		}
		
		return new String(c);
	}
	
}
