package com.liketry.interaction.benison.conf;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.liketry.interaction.benison.sdk.JwtTokenFilter;

/**
 * web配置文件
 * 
 * @author wuke
 *
 */
@Configuration
public class WebConfig {

	/**
	 * jwt令牌过滤器
	 * @return
	 */
	@Bean
	public FilterRegistrationBean jwtTokenFilterRegistrationBean() {
		 
		 FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		 
		 registrationBean.setName("jwtTokenFilter");
		 registrationBean.setFilter(new JwtTokenFilter());
		 registrationBean.setOrder(1);
		 registrationBean.addUrlPatterns("/*");
		 
//		 List<String> urlList = new ArrayList<String>();
//		 urlList.add("/*");
//		 registrationBean.setUrlPatterns(urlList);
		 
		 return registrationBean;
	}

}
