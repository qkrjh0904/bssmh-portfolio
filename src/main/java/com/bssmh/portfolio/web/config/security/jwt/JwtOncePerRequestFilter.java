package com.bssmh.portfolio.web.config.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.bssmh.portfolio.web.config.security.jwt.JwtProperty.JWT_EXCEPTION;
import static com.bssmh.portfolio.web.config.security.jwt.JwtProperty.TOKEN_KEY;

@Component
@RequiredArgsConstructor
public class JwtOncePerRequestFilter extends OncePerRequestFilter {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader(TOKEN_KEY);

        try {
            authenticate(token);
        } catch (ExpiredJwtException e) {
            request.setAttribute(JWT_EXCEPTION, JwtExceptionCode.EXPIRED);
        } catch (UnsupportedJwtException e) {
            request.setAttribute(JWT_EXCEPTION, JwtExceptionCode.UNSUPPORTED);
        } catch (MalformedJwtException e) {
            request.setAttribute(JWT_EXCEPTION, JwtExceptionCode.MALFORMED);
        } catch (SignatureException e) {
            request.setAttribute(JWT_EXCEPTION, JwtExceptionCode.INVALID_SIGNATURE);
        } catch (IllegalArgumentException e) {
            request.setAttribute(JWT_EXCEPTION, JwtExceptionCode.INVALID);
        }

        filterChain.doFilter(request, response);
    }

    private void authenticate(String token) {
        if (Strings.isNotBlank(token)) {
            Authentication auth = jwtAuthenticationProvider.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
    }

}
