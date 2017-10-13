package com.liketry.interaction.benison.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 登录Controller
 * @author wuke
 */
@Controller("loginController")
public class LoginController{

//	@Resource(name=ILoginAction.ACTION_ID)
//	private ILoginAction loginAction;
	
	private static Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	/**
	 * 进入登录页面
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getLogin(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "/view/login.jsp";
	}
	
	/**
	 * 用户登录
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) {

		// 登录验证
		boolean authentication = true;
		
		// 如果验证失败，则返回登录页
		if(!authentication){
			return "/view/login.jsp";
		}
		
		logger.info("用户验证成功！");
		
		// 验证成功后，组装首页内容
		model.addAttribute("operatorName", "赵XX");
		
		return "/view/main.jsp";
	}
	
}
