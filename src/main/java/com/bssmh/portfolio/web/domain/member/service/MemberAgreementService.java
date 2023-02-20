package com.bssmh.portfolio.web.domain.member.service;

import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.db.entity.member.MemberAgreement;
import com.bssmh.portfolio.web.domain.member.repository.MemberAgreementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberAgreementService {

    private final MemberAgreementRepository memberAgreementRepository;

    public void save(Member member) {
        MemberAgreement memberAgreement = MemberAgreement.create(member);
        memberAgreementRepository.save(memberAgreement);
    }
}
