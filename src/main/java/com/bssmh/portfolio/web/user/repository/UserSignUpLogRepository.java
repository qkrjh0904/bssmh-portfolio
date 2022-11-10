package com.bssmh.portfolio.web.user.repository;

import com.bssmh.portfolio.db.entity.user.UserSignUpLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSignUpLogRepository extends JpaRepository<UserSignUpLog, Long> {
}
