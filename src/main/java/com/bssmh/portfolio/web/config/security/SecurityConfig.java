package com.bssmh.portfolio.web.config.security;

import com.bssmh.portfolio.web.config.security.jwt.JwtOncePerRequestFilter;
import com.bssmh.portfolio.web.path.ApiPath;
import com.bssmh.portfolio.web.security.CustomOauth2SuccessHandler;
import com.bssmh.portfolio.web.security.CustomOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtOncePerRequestFilter jwtOncePerRequestFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomOauth2UserService customOauth2UserService;
    private final CustomOauth2SuccessHandler customOauth2SuccessHandler;

    @Bean
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {

        http.httpBasic().disable();
        http.formLogin().disable();
        http.csrf().disable();
        http.cors();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .antMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**").permitAll()
                .antMatchers(ApiPath.ERROR_AUTH).permitAll()
                .antMatchers(ApiPath.BSM_OAUTH, ApiPath.LOGIN_OAUTH2, ApiPath.REFRESH_TOKEN).permitAll()
                .antMatchers(ApiPath.COMMENT_PORTFOLIO_ID).permitAll()
                .antMatchers(ApiPath.FILE_UPLOAD).permitAll()
                .antMatchers(ApiPath.PORTFOLIO_SEARCH).permitAll()
                .antMatchers(ApiPath.MEMBER_ID).permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(jwtOncePerRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler);

        http.oauth2Login()
                .successHandler(customOauth2SuccessHandler)
                .userInfoEndpoint()
                .userService(customOauth2UserService);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
