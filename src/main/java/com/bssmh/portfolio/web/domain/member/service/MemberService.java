package com.bssmh.portfolio.web.domain.member.service;

import com.bssmh.portfolio.web.domain.member.controller.rq.UpdateMemberRq;
import com.bssmh.portfolio.web.domain.member.controller.rs.UpdateMemberRs;
import com.bssmh.portfolio.web.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public UpdateMemberRs updateUser(String name, UpdateMemberRq rq) {

        return null;
    }
}
