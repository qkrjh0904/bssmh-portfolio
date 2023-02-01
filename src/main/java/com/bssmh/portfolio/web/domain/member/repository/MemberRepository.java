package com.bssmh.portfolio.web.domain.member.repository;

import com.bssmh.portfolio.db.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmailAndRegistrationId(String email, String registrationId);
}
