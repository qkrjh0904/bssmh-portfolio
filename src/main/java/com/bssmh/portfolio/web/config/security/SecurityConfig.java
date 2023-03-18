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
                .antMatchers("/resources/**", "/").permitAll()
                // 에러 핸들러
                .antMatchers(ApiPath.ERROR_AUTH).permitAll()
                // 인증
                .antMatchers(ApiPath.BSM_OAUTH, ApiPath.LOGIN_OAUTH2, ApiPath.REFRESH_TOKEN).permitAll()
                // 댓글
                .antMatchers(ApiPath.COMMENT_PORTFOLIO_ID_MATCHER).permitAll()
                // 파일
                .antMatchers(ApiPath.FILE_DOWNLOAD).permitAll()
                // 포트폴리오
                .antMatchers(ApiPath.PORTFOLIO_ID_MATCHER, ApiPath.PORTFOLIO_SEARCH, ApiPath.PORTFOLIO_VIEWS_ADD,
                        ApiPath.PORTFOLIO_MEMBER_ID_MATCHER).permitAll()
                // 멤버
                .antMatchers(ApiPath.MEMBER_ID_MATCHER).permitAll()
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
