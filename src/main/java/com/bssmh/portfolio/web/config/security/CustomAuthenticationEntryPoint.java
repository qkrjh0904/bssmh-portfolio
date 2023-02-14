package com.bssmh.portfolio.web.config.security;

import com.bssmh.portfolio.web.config.security.jwt.JwtExceptionCode;
import com.bssmh.portfolio.web.path.ApiPath;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Objects;

import static com.bssmh.portfolio.web.config.security.jwt.JwtProperty.JWT_EXCEPTION;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException{
        JwtExceptionCode exceptionCode = (JwtExceptionCode) request.getAttribute(JWT_EXCEPTION);
        String encode = URLEncoder.encode("에러 메시지가 없습니다.", "UTF-8");
        if(Objects.nonNull(exceptionCode)){
            encode = URLEncoder.encode(exceptionCode.getMessage(), "UTF-8");
        }
        response.sendRedirect(ApiPath.ERROR_AUTH + "?message=" + encode);
    }
}
