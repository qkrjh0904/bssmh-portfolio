package com.bssmh.portfolio.web.domain.follow.repository;

import com.bssmh.portfolio.db.entity.follow.Follow;
import com.bssmh.portfolio.db.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Optional<Follow> findByFromMemberAndToMember(Member fromMember, Member toMember);
}
