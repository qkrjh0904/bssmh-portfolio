package com.bssmh.portfolio.web.user.repository;

import com.bssmh.portfolio.db.entity.user.UserRegisterLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRegisterLogRepository extends JpaRepository<UserRegisterLog, Long> {
}
