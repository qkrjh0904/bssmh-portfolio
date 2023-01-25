package com.bssmh.portfolio.web.domain.auth.facade;

import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.db.entity.member.MemberLoginLog;
import com.bssmh.portfolio.db.entity.member.MemberSignUpLog;
import com.bssmh.portfolio.web.domain.member.repository.MemberLoginLogRepository;
import com.bssmh.portfolio.web.domain.member.repository.MemberRepository;
import com.bssmh.portfolio.web.domain.member.repository.MemberSignUpLogRepository;
import com.bssmh.portfolio.web.domain.member.service.FindMemberService;
import com.bssmh.portfolio.web.security.OAuthAttributes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class BsmAuthFacade {

    // repository
    private final MemberLoginLogRepository memberLoginLogRepository;
    private final MemberSignUpLogRepository memberSignUpLogRepository;
    private final MemberRepository memberRepository;

    // service
    private final FindMemberService findMemberService;

    public Member saveOrUpdate(OAuthAttributes oAuthAttributes) {
        Member member = findMemberService.findByEmail(oAuthAttributes.getEmail());
        if (Objects.isNull(member)) {
            member = oAuthAttributes.toEntity();
            memberRepository.save(member);
            this.saveSignUpLog(member);
        }

        member.update(oAuthAttributes.getName(), oAuthAttributes.getPicture());
        return member;
    }

    public void saveLoginLog(Member member) {
        MemberLoginLog memberLoginLog = MemberLoginLog.create(member.getEmail(), member.getName(), member);
        memberLoginLogRepository.save(memberLoginLog);
    }

    public void saveSignUpLog(Member member) {
        MemberSignUpLog memberSignUpLog = MemberSignUpLog.create(member.getEmail(), member.getName(), member);
        memberSignUpLogRepository.save(memberSignUpLog);
    }
}
