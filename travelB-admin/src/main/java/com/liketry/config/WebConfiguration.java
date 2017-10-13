package com.liketry.config;



import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


/**
 * Created by liketry
 */
@Slf4j
@Aspect
@Configuration
public class WebConfiguration  {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        source.registerCorsConfiguration("/api/**", config);
        source.registerCorsConfiguration("/sys/**", config);
        source.registerCorsConfiguration("/druid/**", config);
        source.registerCorsConfiguration("/management/**", config);
        source.registerCorsConfiguration("/merchant/**", config);
        source.registerCorsConfiguration("/censor/**", config);
        source.registerCorsConfiguration("/orderRecord/**", config);
        return new CorsFilter(source);
    }

}
