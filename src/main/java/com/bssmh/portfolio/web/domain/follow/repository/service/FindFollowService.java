package com.bssmh.portfolio.web.domain.follow.repository.service;

import com.bssmh.portfolio.db.entity.follow.Follow;
import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.web.config.security.context.MemberContext;
import com.bssmh.portfolio.web.domain.follow.repository.FollowRepository;
import com.bssmh.portfolio.web.domain.member.controller.rs.FindMemberSelfRs;
import com.bssmh.portfolio.web.domain.member.controller.rs.FindOtherMemberRs;
import com.bssmh.portfolio.web.domain.member.repository.MemberRepository;
import com.bssmh.portfolio.web.exception.NoSuchMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FindFollowService {

    // repository
    private final FollowRepository followRepository;

    public Optional<Follow> findByEachMemberId(Long fromMemberId, Long toMemberId) {
        return followRepository.findByFromMemberIdAndToMemberId(fromMemberId, toMemberId);
    }

    public Follow findByEachMemberIdOrElseThrow(Long fromMemberId, Long toMemberId) {
        return followRepository.findByFromMemberIdAndToMemberId(fromMemberId, toMemberId)
                .orElseThrow(NoSuchMemberException::new);
    }

}
