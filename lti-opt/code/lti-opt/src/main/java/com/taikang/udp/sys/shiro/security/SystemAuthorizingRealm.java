package com.taikang.udp.sys.shiro.security;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.taikang.udp.framework.common.util.Encodes;
import com.taikang.udp.framework.common.util.logger.LoggerFactory;
import com.taikang.udp.framework.core.exception.TKCheckedException;
import com.taikang.udp.framework.core.properties.PropertiesHelper;
import com.taikang.udp.sys.action.ISystemAction;
import com.taikang.udp.sys.util.UserUtils;
import com.taikang.udp.sys.util.servlet.ValidateCodeServlet;
import com.taikang.udp.sys.util.vo.LoginUser;
import com.taikang.udp.sys.util.vo.SysUserMenu;

import net.sf.json.JSONObject;

/**
 * 系统安全认证实现类
 * @author Johnny
 */
@Service("systemAuthorizingRealm")
public class SystemAuthorizingRealm extends AuthorizingRealm {
	
	private static final Logger logger = LoggerFactory.getLogger();

	/**
     * 注入systemService
     */	
	@Resource(name=ISystemAction.SERVICE_ID)
	private ISystemAction systemAction;
	
	/**
	 * 清空用户关联权限认证，待下次使用时重新加载
	 */
	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
		clearCachedAuthorizationInfo(principals);
	}
	
	/**
	 * 清空所有关联认证
	 */
	public void clearAllCachedAuthorizationInfo() {
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if (cache != null) {
			for (Object key : cache.keys()) {
				cache.remove(key);
			}
		}
	}
	
	/**
	 * 认证回调函数, 登录时调用
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		
		logger.info("<=====认证回调函数方法，开始登陆===============>");
		
		if (PropertiesHelper.isValidateCodeLogin()){
			// 判断验证码
			Session session = SecurityUtils.getSubject().getSession();
			//获取保存到session的验证码信息
			String code = (String)session.getAttribute(ValidateCodeServlet.VALIDATE_CODE);
			if (token.getCaptcha() == null || !token.getCaptcha().toUpperCase().equals(code)){
				throw new CaptchaException("验证码错误.");
			}
		}
		
		// 调用登录校验UCC方法，校验通过返回登录用户UserBO
		LoginUser user = systemAction.getUserByLoginName(token.getUsername());
		logger.info("<=====登陆用户信息：{}===============>",JSONObject.fromObject(user));
		if (user != null) {
			//验证用户密码的前16位
//			try {
//				String entryptPassword = UserUtils.entryptPassword("admin");
//			} catch (TKCheckedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			byte[] salt = Encodes.decodeHex(user.getUserPwd().substring(0,16));
			return new SimpleAuthenticationInfo(new Principal(user), 
					user.getUserPwd().substring(16), ByteSource.Util.bytes(salt), getName());
		} else {
			return null;
		}
	}
	
	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		logger.info("<=====用户授权开始===============>");
		Principal principal = (Principal) getAvailablePrincipal(principals);
		LoginUser user = systemAction.getUserByLoginName(principal.getLoginName());
		if (user != null) {
			UserUtils.putCache("user", user);
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			List<SysUserMenu> list = UserUtils.getButtonList();
			logger.info("<=====用户添加菜单：{}===============>",list);
			for (SysUserMenu menu : list){
				if (StringUtils.isNotBlank(menu.getPermission())){
					// 添加基于Permission的权限信息
					for (String permission : StringUtils.split(menu.getPermission(),",")){
						info.addStringPermission(permission);
					}
				}
			}
			// 更新登录IP和时间
			systemAction.updateUserLoginInfo(user.getUserId());
			return info;
		} else {
			return null;
		}
	}
	
	/**
	 * 设定密码校验的Hash算法与迭代次数
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(UserUtils.HASH_ALGORITHM);
		matcher.setHashIterations(UserUtils.HASH_INTERATIONS);
		setCredentialsMatcher(matcher);
	}
	
	
	/**
	 * 授权用户信息
	 */
	public static class Principal implements Serializable {

		private static final long serialVersionUID = 1L;
		
		private int id;
		private String loginName;
		private String name;
		private Map<String, Object> cacheMap;

		public Principal(LoginUser user) {
			this.id = user.getUserId();
			this.loginName = user.getUserCode();
			this.name = user.getUserName();
		}

		public int getId() {
			return id;
		}

		public String getLoginName() {
			return loginName;
		}

		public String getName() {
			return name;
		}

		public Map<String, Object> getCacheMap() {
			if (cacheMap==null){
				cacheMap = new HashMap<String, Object>();
			}
			return cacheMap;
		}

	}
	
}
