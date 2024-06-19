package com.dogpamines.dogseek.config;

import com.dogpamines.dogseek.auth.interceptor.JwtTokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/static/", "classpath:/public/", "classpath:/", "classpath:/resources",
            "classpath:/META-INF/resources/", "classpath:/META-INF/resources/webjars"
    };

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    }

    @Bean
    public JwtTokenInterceptor jwtTokenInterceptor() {
        return new JwtTokenInterceptor();
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http:/localhost:3000")
                .allowedMethods("GET","POST","PUT","DELETE")
                .allowedHeaders("*")
                .exposedHeaders(
                        "Content-Type",
                        "Authorization",
                        "Access-Control-Allow-Headers",
                        "Access-Control-Allow-Origin",
                        "Location"
                )
                .allowCredentials(true);
    }
}
