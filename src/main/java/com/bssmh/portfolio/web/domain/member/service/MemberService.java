package com.bssmh.portfolio.web.domain.member.service;

import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.db.entity.member.MemberClassInfo;
import com.bssmh.portfolio.web.config.security.context.MemberContext;
import com.bssmh.portfolio.web.domain.member.controller.rq.SignupMemberRq;
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
    private final MemberAgreementService memberAgreementService;
    private final MemberClassInfoService memberClassInfoService;

    public List<Member> findAllByIdList(List<Long> memberIdList) {
        return memberRepository.findAllById(memberIdList);
    }

    public void updateMember(MemberContext memberContext, UpdateMemberRq rq) {
        Member member = findMemberService.findLoginMember(memberContext);
        member.update(
                rq.getNickName(),
                rq.getDescription(),
                rq.getPhone(),
                rq.getJob(),
                rq.getBelong(),
                rq.getAdmissionYear());

        MemberClassInfo memberClassInfo = memberClassInfoService.findByMemberIdOrElseNull(member.getId());
        memberClassInfoService.upsert(member.getMemberType(), memberClassInfo, rq.getSchoolGrade(), rq.getSchoolClass(), rq.getSchoolNumber(), member);
    }

    public void signupMember(MemberContext memberContext, SignupMemberRq rq) {
        Member member = findMemberService.findLoginMember(memberContext);
        member.update(
                rq.getMemberType(),
                rq.getPhone(),
                rq.getBelong(),
                rq.getAdmissionYear());

        MemberClassInfo memberClassInfo = memberClassInfoService.findByMemberIdOrElseNull(member.getId());
        memberClassInfoService.upsert(rq.getMemberType(), memberClassInfo, rq.getSchoolGrade(), rq.getSchoolClass(), rq.getSchoolNumber(), member);
        memberAgreementService.save(member);
    }
}
