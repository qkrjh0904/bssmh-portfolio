package com.bssmh.portfolio.web.config.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.db.enums.MemberRoleType;
import com.bssmh.portfolio.web.config.security.context.MemberContext;
import com.bssmh.portfolio.web.exception.ExpiredJwtException;
import com.bssmh.portfolio.web.exception.InvalidJwtTokenException;
import com.bssmh.portfolio.web.domain.dto.JwtTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtTokenFactory {

    private static final String JWT_ISSUER = "BSSMH";
    private static final String MEMBER_EMAIL = "MEMBER_EMAIL";
    private static final String REGISTRATION_ID = "REGISTRATION_ID";
    private static final String MEMBER_ROLE_TYPE = "MEMBER_ROLE_TYPE";
    private static final String VALIDITY = "VALIDITY";
    private static final String JWT_SECRET = "BSSMH_JWT_SECRET";

    public JwtTokenDto generateJwtToken(Member member) {
        String validity = LocalDateTime.now().plusHours(2L).toString();
        String token = JWT.create()
                .withIssuer(JWT_ISSUER)
                .withClaim(MEMBER_EMAIL, member.getEmail())
                .withClaim(REGISTRATION_ID, member.getRegistrationId())
                .withClaim(MEMBER_ROLE_TYPE, member.getMemberRoleType().getName())
                .withClaim(VALIDITY, validity)
                .sign(getJwtAlgorithm());

        return JwtTokenDto.builder()
                .token(token)
                .validity(validity)
                .build();
    }

    private Algorithm getJwtAlgorithm() {
        return Algorithm.HMAC256(JWT_SECRET);
    }

    public MemberContext decodeJwtToken(String token) {
        DecodedJWT decodedJWT = verifyToken(token);

        String email = decodedJWT.getClaim(MEMBER_EMAIL).asString();
        String registrationId = decodedJWT.getClaim(REGISTRATION_ID).asString();
        String roleString = decodedJWT.getClaim(MEMBER_ROLE_TYPE).asString();
        MemberRoleType memberRoleType = MemberRoleType.valueOf(roleString);
        String validity = decodedJWT.getClaim(VALIDITY).asString();

        LocalDateTime validityToLocalDateTime = LocalDateTime.parse(validity);
        if(LocalDateTime.now().isAfter(validityToLocalDateTime)){
            throw new ExpiredJwtException();
        }

        return MemberContext.create(email, registrationId, memberRoleType);
    }

    private DecodedJWT verifyToken(String token) {
        JWTVerifier jwtVerifier = JWT.require(getJwtAlgorithm()).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);

        if (Objects.isNull(decodedJWT)) {
            throw new InvalidJwtTokenException();
        }
        return decodedJWT;
    }

}
