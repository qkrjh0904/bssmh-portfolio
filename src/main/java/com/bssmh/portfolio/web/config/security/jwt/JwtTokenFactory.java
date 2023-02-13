package com.bssmh.portfolio.web.config.security.jwt;

import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.web.domain.dto.JwtTokenDto;
import com.bssmh.portfolio.web.utils.DateUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.bssmh.portfolio.web.config.security.jwt.JwtProperty.JWT_ISSUER;
import static com.bssmh.portfolio.web.config.security.jwt.JwtProperty.MEMBER_EMAIL;
import static com.bssmh.portfolio.web.config.security.jwt.JwtProperty.MEMBER_ROLE_TYPE;
import static com.bssmh.portfolio.web.config.security.jwt.JwtProperty.REGISTRATION_ID;
import static com.bssmh.portfolio.web.config.security.jwt.JwtProperty.TOKEN_TIME_TO_LIVE;

@Component
@RequiredArgsConstructor
public class JwtTokenFactory {

    public JwtTokenDto generateJwtToken(Member member) {
        Date now = DateUtils.now();
        Date expiredDate = DateUtils.addTime(now, TOKEN_TIME_TO_LIVE);
        LocalDateTime expiredLocalDateTime = LocalDateTime.ofInstant(expiredDate.toInstant(), ZoneId.systemDefault());
        String token = Jwts.builder()
                .setClaims(createJwtClaims(member))
                .setIssuedAt(now)
                .setIssuer(JWT_ISSUER)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, JwtProperty.SIGN_KEY)
                .compact();

        return JwtTokenDto.builder()
                .token(token)
                .validity(expiredLocalDateTime.toString())
                .build();
    }

    private Map<String, Object> createJwtClaims(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(MEMBER_EMAIL, member.getEmail());
        claims.put(REGISTRATION_ID, member.getRegistrationId());
        claims.put(MEMBER_ROLE_TYPE, member.getMemberRoleType().getName());
        return claims;
    }

    public Claims parseClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(JwtProperty.SIGN_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

}
