package com.bssmh.portfolio.web.config.security;

import com.bssmh.portfolio.web.config.security.context.MemberContext;
import com.bssmh.portfolio.web.config.security.jwt.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtTokenService jwtTokenService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getPrincipal();
        MemberContext memberContext = jwtTokenService.decodeJwtToken(token);
        AuthorizedToken authorizedToken = AuthorizedToken.create(memberContext);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authorizedToken);
        return authorizedToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
