package com.bssmh.portfolio.web.domain.auth.service;

import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.web.config.security.jwt.JwtTokenService;
import com.bssmh.portfolio.web.domain.auth.controller.rq.OAuth2Rq;
import com.bssmh.portfolio.web.domain.dto.JwtTokenDto;
import com.bssmh.portfolio.web.domain.enums.ClientType;
import com.bssmh.portfolio.web.domain.member.service.FindMemberService;
import com.bssmh.portfolio.web.exception.AuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.bssmh.portfolio.web.domain.enums.ClientType.GOOGLE;
import static com.bssmh.portfolio.web.domain.enums.ClientType.KAKAO;

@Service
@RequiredArgsConstructor
@Transactional
public class OAuth2Service {

    // service
    private final OAuth2KaKaoService oAuth2KaKaoService;

    public JwtTokenDto loginOAuth2(OAuth2Rq rq) {
        ClientType clientType = rq.getClientType();
        String code = rq.getCode();

        if (GOOGLE.equals(clientType)) {
            return null;
        }

        if (KAKAO.equals(clientType)) {
            return oAuth2KaKaoService.getToken(code);
        }

        throw new AuthenticationException();
    }


}
