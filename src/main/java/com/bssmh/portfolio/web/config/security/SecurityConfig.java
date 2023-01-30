package com.bssmh.portfolio.web.config.security;

import com.bssmh.portfolio.web.path.ApiPath;
import com.bssmh.portfolio.web.security.CustomOauth2SuccessHandler;
import com.bssmh.portfolio.web.security.CustomOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;

@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOauth2UserService customOauth2UserService;
    private final CustomOauth2SuccessHandler customOauth2SuccessHandler;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        // rest api security 설정
        http.httpBasic().disable();
        http.csrf().disable();
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.cors();

        http.addFilterBefore(jwtOncePerRequestFilter(), UsernamePasswordAuthenticationFilter.class);

        http.authorizeRequests()
                .antMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**").permitAll()
                .antMatchers(ApiPath.BSM_OAUTH).permitAll()
                .anyRequest().permitAll();

        http.oauth2Login()
                .successHandler(customOauth2SuccessHandler)
                .userInfoEndpoint()
                .userService(customOauth2UserService);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(jwtAuthenticationProvider);
    }

    private Filter jwtOncePerRequestFilter() throws Exception {
        return new JwtOncePerRequestFilter(super.authenticationManagerBean());
    }

}
