package com.bssmh.portfolio.web.domain.member.repository;

import com.bssmh.portfolio.db.entity.member.MemberLoginLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberLoginLogRepository extends JpaRepository<MemberLoginLog, Long> {
}
