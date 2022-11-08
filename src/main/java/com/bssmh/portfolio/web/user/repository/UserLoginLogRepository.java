package com.bssmh.portfolio.web.user.repository;

import com.bssmh.portfolio.db.entity.user.UserLoginLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLoginLogRepository extends JpaRepository<UserLoginLog, Long> {
}
