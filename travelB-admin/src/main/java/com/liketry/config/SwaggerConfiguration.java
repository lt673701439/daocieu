package com.liketry.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration  
@EnableSwagger2 
public class SwaggerConfiguration {

    @Bean  
    public Docket swaggerSpringMvcPlugin() {  
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
        		.apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
        		.select()
        		.apis(RequestHandlerSelectors.basePackage("com.liketry.web")) 
        		.paths(PathSelectors.any())
//        		.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))//只显示被ApiOperation注释过得接口
        		.build();  
        		
        return docket;  
    }  
  
    private ApiInfo apiInfo() { 
        return new ApiInfoBuilder().title("到此一游测试平台") 
                .description("在这里你可以浏览项目所有接口，并提供相关测试工具") 
                .version("0.9.0") 
                .build(); 
    }
    
}
