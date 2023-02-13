package com.bssmh.portfolio.web.config.security;

import com.bssmh.portfolio.web.config.security.jwt.JwtExceptionCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.bssmh.portfolio.web.config.security.jwt.JwtProperty.JWT_EXCEPTION;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        JwtExceptionCode exceptionCode = (JwtExceptionCode) request.getAttribute(JWT_EXCEPTION);
    }
}
