package com.bssmh.portfolio.web.domain.member.service;

import com.bssmh.portfolio.db.entity.follow.Follow;
import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.web.config.security.context.MemberContext;
import com.bssmh.portfolio.web.domain.follow.repository.FollowRepository;
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
    private final FollowRepository followRepository;

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

    public FindOtherMemberRs findOtherMember(MemberContext memberContext, Long memberId) {
        Member member = this.findByIdOrElseThrow(memberId);
        Member loginMember = this.findLoginMember(memberContext);
        Follow follow = followRepository.findByFromMemberAndToMember(loginMember, member)
                .orElse(null);
        return FindOtherMemberRs.create(member, Objects.nonNull(follow));
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
