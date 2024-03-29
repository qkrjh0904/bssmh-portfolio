package com.bssmh.portfolio.web.config.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.bssmh.portfolio.web.config.web.CorsPatternConstant.*;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowedMethods("*")
                .allowedOriginPatterns(CORS_LOCAL, CORS_BSSM_PR, CORS_BSSM_PR_FE);
    }

}
