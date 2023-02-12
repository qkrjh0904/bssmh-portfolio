package com.bssmh.portfolio.web.domain.member.service;

import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.web.config.security.context.MemberContext;
import com.bssmh.portfolio.web.domain.member.controller.rs.FindMemberSelfRs;
import com.bssmh.portfolio.web.domain.member.controller.rs.FindOtherMemberRs;
import com.bssmh.portfolio.web.domain.member.repository.MemberRepository;
import com.bssmh.portfolio.web.exception.NoSuchMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FindMemberService {

    private final MemberRepository memberRepository;

    public Member findByEmailAndRegistrationIdOrElseNull(String email, String registrationId) {
        return memberRepository.findByEmailAndRegistrationId(email, registrationId)
                .orElse(null);
    }

    private Member findByEmailAndRegistrationIdOrElseThrow(String email, String registrationId) {
        return memberRepository.findByEmailAndRegistrationId(email, registrationId)
                .orElseThrow(NoSuchMemberException::new);
    }

    public FindMemberSelfRs findMemberSelf(MemberContext memberContext) {
        Member member = this.findLoginMember(memberContext);
        return FindMemberSelfRs.create(member);
    }

    public Member findByIdOrElseThrow(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(NoSuchMemberException::new);
    }

    public FindOtherMemberRs findOtherMember(Long memberId) {
        Member member = this.findByIdOrElseThrow(memberId);
        return FindOtherMemberRs.create(member);
    }

    public Member findLoginMember(MemberContext memberContext) {
        if (Objects.isNull(memberContext)) {
            return null;
        }

        String email = memberContext.getEmail();
        String registrationId = memberContext.getRegistrationId();
        return this.findByEmailAndRegistrationIdOrElseThrow(email, registrationId);
    }
}
