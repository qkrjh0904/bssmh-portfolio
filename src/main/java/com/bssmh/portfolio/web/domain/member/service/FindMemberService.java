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

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FindMemberService {

    private final MemberRepository memberRepository;

    public Member findByEmailOrElseNull(String email) {
        return memberRepository.findByEmail(email)
                .orElse(null);
    }

    public Member findByEmailOrElseThrow(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(NoSuchMemberException::new);
    }

    public List<Member> findAllByName(String search) {
        return memberRepository.findAllByName(search);
    }

    public FindMemberSelfRs findMemberSelf(String email) {
        Member member = this.findByEmailOrElseThrow(email);

        return FindMemberSelfRs.builder()
                .memberId(member.getId())
                .name(member.getName())
                .profileImageUrl(member.getProfileImageUrl())
                .email(member.getEmail())
                .email(member.getEmail())
                .description(member.getDescription())
                .memberRoleType(member.getMemberRoleType())
                .build();
    }

    public Member findByIdOrElseThrow(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(NoSuchMemberException::new);
    }

    public FindOtherMemberRs findOtherMember(MemberContext memberContext, Long memberId) {
        // TODO: 2023-01-11 권한 인증 필요

        Member member = this.findByIdOrElseThrow(memberId);
        return FindOtherMemberRs.builder()
                .memberId(member.getId())
                .name(member.getName())
                .profileImageUrl(member.getProfileImageUrl())
                .email(member.getEmail())
                .description(member.getDescription())
                .build();
    }

}
