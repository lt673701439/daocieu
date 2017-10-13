package com.liketry.interaction.benison.util;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.liketry.interaction.benison.vo.UserMenu;

/**
 * 用户工具类
 * @author wuke
 */
public class UserUtils {
	
	private static Logger logger = LoggerFactory.getLogger(UserUtils.class);
	
	private static String decryptKey = "likebenisontry";//解码KEY
	
	public static List<UserMenu> getMenuList(){
		
		List<UserMenu> menuList = new ArrayList<UserMenu>();
//		LoginUser loginUser = getUser();
//		Dto param = new BaseDto();			
//		param.put("userId", loginUser.getUserId());
//		List<Dto> menuDtoList = appDao.queryForMapList("Login.findAllMenuByUserId", param);
//		
//		/** 将查询到的菜单信息转换为UserMenu对象*/
//		for (Dto oneMenu : menuDtoList) {
//			UserMenu menu = new UserMenu();
//			menu.setMenuId(String.valueOf(oneMenu.get("menu_id")));
//			menu.setMenuName(String.valueOf(oneMenu.get("menu_name")));
//			menu.setParentId(String.valueOf(oneMenu.get("parent_id")));
//			menu.setParentName(String.valueOf( oneMenu.get("parent_name")));
//			menu.setUrl(String.valueOf(oneMenu.get("url") + "?reqMenuId=" + oneMenu.get("menu_id")));
//			menu.setIcon(String.valueOf(oneMenu.get("icon")));
//			menu.setIsShow(String.valueOf(oneMenu.get("is_show")));
//			menu.setPermission(String.valueOf(oneMenu.get("permission")));
//							
//			menuList.add(menu);
//		}
//		
		/*  
         * 第一个参数是第几页；第二个参数是每页显示条数。  
         */
		Page page = PageHelper.startPage(1,10);
//		mapper.selectAll();
		
		logger.info("获取所有Doctor信息，获得记录数：{}", page.size());
		logger.info("获取所有Doctor信息，获得记录：{}", page);
		
		return menuList;
	}
	
	/**
	 * 获取当天日期和时间
	 * @return
	 */
	public static Date getCurrentDate(){
		
		logger.info("<======UserUtils.getCurrentDate=====start===========>");
		
		LocalDateTime localDateTime = LocalDateTime.now();
	    ZoneId zone = ZoneId.systemDefault();
	    Instant instant = localDateTime.atZone(zone).toInstant();
	    
	    return Date.from(instant);
	}
	
	/**
	 * 请求参数解码
	 * @param data
	 * @return
	 */
	public static JSONObject decrypt2Str(String data){
		
		String res = null;
		 
		if(!StringUtils.isEmpty(data)){
			
			String baseDecodeStr = Base64Coder.decodeString(data);
			//判断key值是否存在
			int position = baseDecodeStr.indexOf(decryptKey);
			
			if(position>-1){
				res = Base64Coder.decodeString(baseDecodeStr.replace(decryptKey, ""));
			}	
		}
		
		logger.info("<====UserUtils.decrypt2Str==入参json:"+res+"======>");
		
		return JSONObject.parseObject(res);
	}
	
	
}
