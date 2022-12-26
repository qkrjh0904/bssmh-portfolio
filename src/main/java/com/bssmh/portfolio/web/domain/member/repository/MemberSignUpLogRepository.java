package com.bssmh.portfolio.web.domain.member.repository;

import com.bssmh.portfolio.db.entity.member.MemberSignUpLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberSignUpLogRepository extends JpaRepository<MemberSignUpLog, Long> {
}
