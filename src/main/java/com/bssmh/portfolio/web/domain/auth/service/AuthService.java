package com.bssmh.portfolio.web.domain.auth.service;

import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.web.config.security.context.MemberContext;
import com.bssmh.portfolio.web.config.security.jwt.JwtTokenFactory;
import com.bssmh.portfolio.web.domain.dto.JwtTokenDto;
import com.bssmh.portfolio.web.domain.member.service.FindMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final FindMemberService findMemberService;
    private final JwtTokenFactory jwtTokenFactory;

    public JwtTokenDto refreshToken(MemberContext memberContext) {
        Member loginMember = findMemberService.findLoginMember(memberContext);
        return jwtTokenFactory.generateJwtToken(loginMember);
    }
}
