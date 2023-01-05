package com.bssmh.portfolio.web.domain.member.service;

import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.web.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FindMemberService {

    private final MemberRepository memberRepository;

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElse(null);
    }

    public List<Member> findAllByName(String search) {
        return memberRepository.findAllByName(search);
    }
}
