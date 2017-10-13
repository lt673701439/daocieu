package com.taikang.udp.sys.shiro.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.taikang.udp.framework.common.util.logger.LoggerFactory;

/**
 * 表单验证（包含验证码）过滤类
 * @author Johnny
 */
@Service
public class FormAuthenticationFilter extends org.apache.shiro.web.filter.authc.FormAuthenticationFilter {

	public static final String DEFAULT_CAPTCHA_PARAM = "validateCode";
	
	private static final Logger logger = LoggerFactory.getLogger();

	private String captchaParam = DEFAULT_CAPTCHA_PARAM;
	
	public String getCaptchaParam() {
		return captchaParam;
	}

	protected String getCaptcha(ServletRequest request) {
		return WebUtils.getCleanParam(request, getCaptchaParam());
	}
	
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		
		String username = getUsername(request);
		String password = getPassword(request);
		if (password==null){
			password = "";
		}
		boolean rememberMe = isRememberMe(request);
		String host = getHost(request);
		String captcha = getCaptcha(request);
		logger.info("<=====创建token======username:{},password:{},host:{}===============>",username,password,host);
		return new UsernamePasswordToken(username, password.toCharArray(), rememberMe, host, captcha);
	}

}
