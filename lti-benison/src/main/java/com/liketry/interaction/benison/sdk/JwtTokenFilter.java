package com.liketry.interaction.benison.sdk;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

/**
 * jwt令牌过滤器
 * 
 * @author wuke
 *
 */
public class JwtTokenFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		
		final HttpServletRequest request = (HttpServletRequest) req;
		final HttpServletResponse response = (HttpServletResponse) res;
	    final String authHeader = request.getHeader("authorization");

	    // 当客户端想要决定其他可用的方法来检索或者处理Web服务端的一个文档时使用:OPTIONS
	    if ("OPTIONS".equals(request.getMethod())) {
	        response.setStatus(HttpServletResponse.SC_OK);

	        chain.doFilter(req, res);
	    } else {

	    	// 获得用户请求的URI
		    String path = request.getRequestURI();
	    
		    // 登陆页面及登录操作无需过滤（app端登录、小程序登录、微信回调、退单、退款）
		    if("/lti-benison/login_api/login".equals(path) || "/lti-benison/login_api/getOid".equals(path)
		    		|| "/lti-benison/wechat_api/returnmsg".equals(path) || "/lti-benison/order_api/returnOrder".equals(path)
		    		|| "/lti-benison/order_api/orderRefund".equals(path) || "/lti-benison/spot_api/getSpotAll".equals(path)
		    		|| path.contains("/lti-benison/qrCode/") || "/lti-benison/qr_code_api/create".equals(path)){
		    	chain.doFilter(req, res);
		    }else{
		    	// 校验header格式
		    	if (authHeader == null || !authHeader.startsWith("Bearer ")) {
		    		throw new ServletException("JWT异常：无效的 Authorization header");
		    	}
		    	
		    	// 获得令牌
		    	final String jwtToken = authHeader.substring(7);
		    	//		final String jwtToken = request.getParameter("jwtToken");
		    	
		    	// 判断令牌是否为空
		    	if(jwtToken == null){
		    		throw new ServletException("令牌为空！");
		    	}
		    	
		    	// 判断令牌是否有效
		    	try {
		    		final Claims claims = Jwts.parser().setSigningKey("liketrybenison").parseClaimsJws(jwtToken).getBody();
		    		request.setAttribute("claims", claims);
		    		
		    	} catch (final SignatureException e) {
		    		throw new ServletException("JWT异常：无效的令牌！");
		    	}
		    	
		    	chain.doFilter(req, res);
		    }
		    
	    }
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
