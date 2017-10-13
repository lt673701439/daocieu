package com.taikang.udp.sys.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;

import com.google.common.collect.Maps;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import com.taikang.udp.framework.common.util.Encodes;
import com.taikang.udp.framework.common.util.logger.LoggerFactory;
import com.taikang.udp.framework.core.exception.TKCheckedException;
import com.taikang.udp.framework.core.persistence.dao.IBaseDao;
import com.taikang.udp.framework.core.spring.SpringBeanLoader;
import com.taikang.udp.sys.action.ISystemAction;
import com.taikang.udp.sys.model.DictEntryBO;
import com.taikang.udp.sys.model.RoleBO;
import com.taikang.udp.sys.model.UserBO;
import com.taikang.udp.sys.service.IDictEntryService;
import com.taikang.udp.sys.shiro.security.SystemAuthorizingRealm.Principal;
import com.taikang.udp.sys.shiro.security.tools.Digests;
import com.taikang.udp.sys.util.vo.LoginUser;
import com.taikang.udp.sys.util.vo.SysUserMenu;

import net.sf.json.JSONObject;

/**
 * 用户工具类
 * @author Johnny
 */
public class UserUtils {
	
	public static final String CACHE_USER = "loginUser";
	public static final String CACHE_ROLE_LIST = "roleList";
	public static final String CACHE_MENU_LIST = "menuList";
	public static final String CACHE_AREA_LIST = "areaList";
	public static final String CACHE_BUTTON_LIST = "buttonList";
	
	private static IBaseDao appDao =  SpringBeanLoader.getSpringBean("appDao");
	
	private static ISystemAction systemAction = SpringBeanLoader.getSpringBean(ISystemAction.SERVICE_ID);
	
	private static final Logger logger = LoggerFactory.getLogger();
	
	public static LoginUser getUser(){
		//缓存中是否存有user对象
		LoginUser loginUser = (LoginUser)getCache(CACHE_USER);
		logger.info("<=====判断缓存中是否有user对象,loginUser:{}=====================>",JSONObject.fromObject(loginUser));
		if (loginUser == null){
			try{
				Subject subject = SecurityUtils.getSubject();
				Principal principal = (Principal)subject.getPrincipal();
				if (principal!=null){
					loginUser = systemAction.getUser(principal.getId());
					putCache(CACHE_USER, loginUser);
				}
			}catch (UnavailableSecurityManagerException e) {
				
			}catch (InvalidSessionException e){
				
			}
		}
		if (loginUser == null){
			loginUser = new LoginUser(new UserBO());
			try{
				SecurityUtils.getSubject().logout();
			}catch (UnavailableSecurityManagerException e) {
				
			}catch (InvalidSessionException e){
				
			}
		}
		return loginUser;
	}
	
	public static LoginUser getUser(boolean isRefresh){
		if (isRefresh){
			removeCache(CACHE_USER);
		}
		return getUser();
	}
	
	public static List<RoleBO> getRoleList(){
		@SuppressWarnings("unchecked")
		List<RoleBO> list = (List<RoleBO>)getCache(CACHE_ROLE_LIST);
		logger.info("<=====获取用户的角色列表:{}=====================>",list);
		if (list == null){
			LoginUser loginUser = getUser();
			Dto param = new BaseDto();			
			param.put("userId", loginUser.getUserId());
			
			List<Dto> roleList = appDao.queryForMapList("Login.findRoleByUserId", param);
			list = new ArrayList<RoleBO>();
			for(Dto oneRole:roleList){
				list.add(BaseDto.toModel(RoleBO.class, oneRole));
			}
			putCache(CACHE_ROLE_LIST, list);
		}
		return list;
	}
	
	public static List<SysUserMenu> getMenuList(){
		@SuppressWarnings("unchecked")
		List<SysUserMenu> menuList = (List<SysUserMenu>)getCache(CACHE_MENU_LIST);
		logger.info("<=====获取用户的菜单列表:{}=====================>",menuList);
		if (menuList == null || menuList.size() == 0){
			LoginUser loginUser = getUser();
			Dto param = new BaseDto();			
			param.put("userId", loginUser.getUserId());
			List<Dto> menuDtoList = appDao.queryForMapList("Login.findAllMenuByUserId", param);
			
			/** 将查询到的菜单信息转换为SysMenuMenu对象*/
			menuList = new ArrayList<SysUserMenu>();
			for (Dto oneMenu : menuDtoList) {
				SysUserMenu menu = new SysUserMenu();
				menu.setMenuId(String.valueOf(oneMenu.get("menu_id")));
				menu.setMenuName(String.valueOf(oneMenu.get("menu_name")));
				menu.setParentId(String.valueOf(oneMenu.get("parent_id")));
				menu.setParentName(String.valueOf( oneMenu.get("parent_name")));
				menu.setUrl(String.valueOf(oneMenu.get("url") + "?reqMenuId=" + oneMenu.get("menu_id")));
				menu.setIcon(String.valueOf(oneMenu.get("icon")));
				menu.setIsShow(String.valueOf(oneMenu.get("is_show")));
				menu.setPermission(String.valueOf(oneMenu.get("permission")));
								
				menuList.add(menu);
			}
			
			putCache(CACHE_MENU_LIST, menuList);
		}
		return menuList;
	}
	
	/**
	 * 获取按钮授权信息<br/>
	 * @return   
	 * List<SysUserMenu>
	 */
	public static List<SysUserMenu> getButtonList(){
		@SuppressWarnings("unchecked")
		List<SysUserMenu> menuList = (List<SysUserMenu>)getCache(CACHE_BUTTON_LIST);
		if (menuList == null){
			LoginUser loginUser = getUser();
			Dto param = new BaseDto();			
			param.put("userId", loginUser.getUserId());
			List<Dto> menuDtoList = appDao.queryForMapList("Login.findButtonPermission", param);
			
			/** 将查询到的菜单信息转换为SysMenuMenu对象*/
			menuList = new ArrayList<SysUserMenu>();
			for (Dto oneMenu : menuDtoList) {
				SysUserMenu menu = new SysUserMenu();
				menu.setMenuId(String.valueOf(oneMenu.get("menu_id")));
				menu.setMenuName(String.valueOf(oneMenu.get("menu_name")));
				menu.setParentId(String.valueOf(oneMenu.get("parent_id")));
				menu.setParentName(String.valueOf( oneMenu.get("parent_name")));
				menu.setUrl(String.valueOf(oneMenu.get("url") + "?reqMenuId=" + oneMenu.get("menu_id")));
				menu.setIcon(String.valueOf(oneMenu.get("icon")));
				menu.setIsShow(String.valueOf(oneMenu.get("is_show")));
				menu.setPermission(String.valueOf(oneMenu.get("permission")));
								
				menuList.add(menu);
			}
			
			putCache(CACHE_BUTTON_LIST, menuList);
		}
		return menuList;
	}
		
	public static LoginUser getUserById(String userId){
		if(StringUtils.isNotBlank(userId)) {
			return systemAction.getUser(Integer.valueOf(userId));
		} else {
			return null;
		}
	}
	
	// ============== LoginUser Cache ==============
	
	public static Object getCache(String key) {
		return getCache(key, null);
	}
	
	public static Object getCache(String key, Object defaultValue) {
		Object obj = getCacheMap().get(key);
		return obj==null?defaultValue:obj;
	}

	public static void putCache(String key, Object value) {
		getCacheMap().put(key, value);
	}

	public static void removeCache(String key) {
		getCacheMap().remove(key);
	}
	
	public static Map<String, Object> getCacheMap(){
		Map<String, Object> map = Maps.newHashMap();
		try{
			Subject subject = SecurityUtils.getSubject();
			Principal principal = (Principal)subject.getPrincipal();
			return principal!=null?principal.getCacheMap():map;
		}catch (UnavailableSecurityManagerException e) {
			
		}catch (InvalidSessionException e){
			
		}
		return map;
	}
	
	/**------------通用的用户密码加解密方法---------------**/
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	public static final int SALT_SIZE = 8;
	
	
	/**
	 * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
	 * @throws TKCheckedException 
	 */
	public static String entryptPassword(String plainPassword) throws TKCheckedException {		
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
		return Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword);		
	}
	
	/**
	 * 验证密码
	 * @param plainPassword 明文密码
	 * @param password 密文密码
	 * @return 验证成功返回true
	 * @throws TKCheckedException 
	 */
	public static boolean validatePassword(String plainPassword, String password) throws TKCheckedException {
		byte[] salt = Encodes.decodeHex(password.substring(0,16));
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
		return password.equals(Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword));
	}
}
