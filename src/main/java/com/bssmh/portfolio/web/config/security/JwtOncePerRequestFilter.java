package com.bssmh.portfolio.web.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtOncePerRequestFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;
    private static final String TOKEN_KEY = "token";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(TOKEN_KEY);
        if (StringUtils.hasText(token)) {
            JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(token);
            authenticationManager.authenticate(jwtAuthenticationToken);
        }

        filterChain.doFilter(request, response);
    }

}
