package com.bssmh.portfolio.web.domain.follow.repository;

import com.bssmh.portfolio.db.entity.follow.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFromMemberIdAndToMemberId(Long fromMemberId, Long toMemberId);
}
