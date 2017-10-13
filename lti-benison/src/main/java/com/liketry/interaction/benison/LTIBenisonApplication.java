package com.liketry.interaction.benison;

import com.liketry.interaction.benison.constants.SystemConstants;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 项目的启动类，指定扫描的包路径
 * @author wuke
 */
@MapperScan("com.liketry.interaction.benison.dao")
@EnableTransactionManagement
@SpringBootApplication
public class LTIBenisonApplication extends WebMvcConfigurerAdapter
{

	public static void main(String[] args)
	{
		SpringApplication.run(LTIBenisonApplication.class, args);
	}
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler(SystemConstants.URL_QR_CODE + "**").addResourceLocations("classpath:/" + SystemConstants.ROOT_LOCAL + SystemConstants.QR_CODE_LOCAL);
//	}
}
