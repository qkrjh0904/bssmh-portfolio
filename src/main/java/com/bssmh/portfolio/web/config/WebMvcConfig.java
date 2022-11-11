package com.bssmh.portfolio.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Duration;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowedMethods("*")
                .allowedOriginPatterns("http*://*.asdfasdf39asasijb38bc81d");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        String staticResourceLocation = "classpath:/static/";
//        registry.addResourceHandler("/static/**")
//                .addResourceLocations(staticResourceLocation)
//                .setCacheControl(CacheControl.maxAge(Duration.ofDays(1)));
    }
}