package com.bssmh.portfolio.web.config.security.jwt;

import com.bssmh.portfolio.db.enums.MemberRoleType;
import com.bssmh.portfolio.web.config.security.context.MemberContext;
import com.bssmh.portfolio.web.config.security.jwt.JwtTokenFactory;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import static com.bssmh.portfolio.web.config.security.jwt.JwtProperty.MEMBER_EMAIL;
import static com.bssmh.portfolio.web.config.security.jwt.JwtProperty.MEMBER_ROLE_TYPE;
import static com.bssmh.portfolio.web.config.security.jwt.JwtProperty.REGISTRATION_ID;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider {

    private final JwtTokenFactory jwtTokenFactory;

    public Authentication authenticate(String token) throws AuthenticationException {
        Claims claims = jwtTokenFactory.parseClaims(token);

        String email = claims.get(MEMBER_EMAIL, String.class);
        String registrationId = claims.get(REGISTRATION_ID, String.class);
        String roleString = claims.get(MEMBER_ROLE_TYPE, String.class);
        MemberRoleType memberRoleType = MemberRoleType.valueOf(roleString);
        MemberContext memberContext = MemberContext.create(email, registrationId, memberRoleType);

        return new UsernamePasswordAuthenticationToken(memberContext, null, null);
    }

}
