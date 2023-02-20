package com.bssmh.portfolio.web.domain.member.repository;

import com.bssmh.portfolio.db.entity.member.MemberClassInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberClassInfoRepository extends JpaRepository<MemberClassInfo, Long> {
    Optional<MemberClassInfo> findByMemberIdAndSchoolGrade(Long memberId, Integer schoolGrade);
}
