package com.taikang.udp.sys.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taikang.udp.framework.core.web.BaseController;
import com.taikang.udp.sys.util.UserUtils;
import com.taikang.udp.sys.util.vo.LoginUser;

/**
 * 登录Controller
 * @author Johnny
 */
@Controller("loginController")
public class LoginController extends BaseController {

	/**
	 * 管理登录
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
		LoginUser user = UserUtils.getUser();
		// 如果已经登录，则跳转到管理首页
		if(user.getUserId() != null){
			logger.info("<=======跳转到主页面=====userId:{}===========>",user.getUserId());
			return "redirect: /main";
		}
		logger.info("<=======跳转到登陆页面===============>");
		return "login";
	}
		
	/**
	 * 登录失败，真正登录的POST请求由Filter完成
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String username, HttpServletRequest request, HttpServletResponse response, Model model) {
		LoginUser user = UserUtils.getUser();
		// 如果已经登录，则跳转到管理首页
		if(user.getUserId() != null){
			logger.info("<=======跳转到主页面=====userId:{}===========>",user.getUserId());
			return "redirect: /main";
		}
		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
		
		return "login";
	}	
}
