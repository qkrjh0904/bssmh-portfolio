package com.bssmh.portfolio.web.domain.auth.service;

import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.db.entity.member.MemberLoginLog;
import com.bssmh.portfolio.db.entity.member.MemberSignUpLog;
import com.bssmh.portfolio.web.domain.member.repository.MemberLoginLogRepository;
import com.bssmh.portfolio.web.domain.member.repository.MemberRepository;
import com.bssmh.portfolio.web.domain.member.repository.MemberSignUpLogRepository;
import com.bssmh.portfolio.web.domain.member.service.FindMemberService;
import com.bssmh.portfolio.web.security.OAuthAttributes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class OAuth2LoginService {

    private final FindMemberService findMemberService;
    private final MemberLoginLogRepository memberLoginLogRepository;
    private final MemberSignUpLogRepository memberSignUpLogRepository;
    private final MemberRepository memberRepository;


    public Member saveOrUpdate(OAuthAttributes oAuthAttributes) {
        Member member = findMemberService.findByEmailAndRegistrationIdOrElseNull(oAuthAttributes.getEmail(),
                oAuthAttributes.getRegistrationId());
        if (Objects.isNull(member)) {
            member = oAuthAttributes.toEntity();
            memberRepository.save(member);
            this.saveSignUpLog(member);
        }

        member.update(oAuthAttributes.getPicture());
        return member;
    }

    public void saveLoginLog(Member member) {
        MemberLoginLog memberLoginLog = MemberLoginLog.create(member.getEmail(), member.getName(), member);
        memberLoginLogRepository.save(memberLoginLog);
    }

    private void saveSignUpLog(Member member) {
        MemberSignUpLog memberSignUpLog = MemberSignUpLog.create(member.getEmail(), member.getName(), member);
        memberSignUpLogRepository.save(memberSignUpLog);
    }


}
