package com.bssmh.portfolio.web.domain.member.service;

import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.web.config.security.context.MemberContext;
import com.bssmh.portfolio.web.domain.member.controller.rq.UpdateMemberRq;
import com.bssmh.portfolio.web.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final FindMemberService findMemberService;

    public List<Member> findAllByIdList(List<Long> memberIdList) {
        return memberRepository.findAllById(memberIdList);
    }

    public void updateMember(MemberContext memberContext, UpdateMemberRq rq) {
        String email = memberContext.getEmail();
        String registrationId = memberContext.getRegistrationId();
        Member member = findMemberService.findByEmailAndRegistrationIdOrElseThrow(email, registrationId);
        member.update(
                rq.getNickName(),
                rq.getDescription(),
                rq.getPhone(),
                rq.getJob());
    }

}
