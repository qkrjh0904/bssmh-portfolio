package com.bssmh.portfolio.web.domain.member.repository;

import com.bssmh.portfolio.db.entity.member.MemberAgreement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberAgreementRepository extends JpaRepository<MemberAgreement, Long> {
}
