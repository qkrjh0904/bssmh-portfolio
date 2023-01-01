package com.bssmh.portfolio.web.security;

import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.db.entity.member.MemberLoginLog;
import com.bssmh.portfolio.db.entity.member.MemberSignUpLog;
import com.bssmh.portfolio.web.config.security.jwt.JwtTokenService;
import com.bssmh.portfolio.web.domain.dto.JwtTokenDto;
import com.bssmh.portfolio.web.domain.member.repository.MemberLoginLogRepository;
import com.bssmh.portfolio.web.domain.member.repository.MemberRepository;
import com.bssmh.portfolio.web.domain.member.repository.MemberSignUpLogRepository;
import com.bssmh.portfolio.web.domain.member.service.FindMemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CustomOauth2SuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;

    // repository
    private final MemberLoginLogRepository memberLoginLogRepository;
    private final MemberSignUpLogRepository memberSignUpLogRepository;
    private final MemberRepository memberRepository;

    // service
    private final FindMemberService findMemberService;
    private final JwtTokenService jwtTokenService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuthAttributes oAuthAttributes = (OAuthAttributes) authentication.getPrincipal();
        Member member = this.saveOrUpdate(oAuthAttributes);
        this.saveLoginLog(member);

        JwtTokenDto jwtTokenDto = jwtTokenService.generateJwtToken(member);
        writeTokenResponse(response, jwtTokenDto);
    }

    private void writeTokenResponse(HttpServletResponse response, JwtTokenDto jwtTokenDto) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.addHeader("token", jwtTokenDto.getToken());
        response.addHeader("validity", jwtTokenDto.getValidity());
        response.setContentType("application/json;charset=UTF-8");

        PrintWriter printWriter = response.getWriter();
        printWriter.println(objectMapper.writeValueAsString(jwtTokenDto));
        printWriter.flush();
    }

    private Member saveOrUpdate(OAuthAttributes oAuthAttributes) {
        Member member = findMemberService.findByEmail(oAuthAttributes.getEmail());
        if (Objects.isNull(member)) {
            member = oAuthAttributes.toEntity();
            memberRepository.save(member);
            this.saveSignUpLog(member);
        }

        member.update(oAuthAttributes.getName(), oAuthAttributes.getPicture());
        return member;
    }

    private void saveLoginLog(Member member) {
        MemberLoginLog memberLoginLog = MemberLoginLog.of(member.getEmail(), member.getName(), member);
        memberLoginLogRepository.save(memberLoginLog);
    }

    private void saveSignUpLog(Member member) {
        MemberSignUpLog memberSignUpLog = MemberSignUpLog.of(member.getEmail(), member.getName(), member);
        memberSignUpLogRepository.save(memberSignUpLog);
    }

}
