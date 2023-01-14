package com.bssmh.portfolio.web.domain.auth.service;

import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.web.config.security.jwt.JwtTokenService;
import com.bssmh.portfolio.web.domain.dto.JwtTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    // service
    private final JwtTokenService jwtTokenService;

    public JwtTokenDto loginPostProcess(Member member) {
        return jwtTokenService.generateJwtToken(member);
    }
}
