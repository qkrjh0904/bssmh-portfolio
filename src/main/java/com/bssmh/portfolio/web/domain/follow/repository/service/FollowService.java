package com.bssmh.portfolio.web.domain.follow.repository.service;

import com.bssmh.portfolio.db.entity.follow.Follow;
import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.web.config.security.context.MemberContext;
import com.bssmh.portfolio.web.domain.follow.repository.FollowRepository;
import com.bssmh.portfolio.web.domain.follow.repository.controller.rq.FollowMemberRq;
import com.bssmh.portfolio.web.domain.member.service.FindMemberService;
import com.bssmh.portfolio.web.exception.AlreadyFollowedException;
import com.bssmh.portfolio.web.exception.SelfFollowNotAllowedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {

    // service
    private final FindFollowService findFollowService;
    private final FindMemberService findMemberService;

    // repository
    private final FollowRepository followRepository;

    public void follow(MemberContext memberContext, FollowMemberRq rq) {
        String email = memberContext.getEmail();
        Member fromMember = findMemberService.findByEmailOrElseThrow(email);
        if (fromMember.getId().equals(rq.getMemberId())) throw new SelfFollowNotAllowedException();
        Member toMember = findMemberService.findByIdOrElseThrow(rq.getMemberId());

        Optional<Follow> follow = findFollowService.findByEachMember(fromMember, toMember);
        if (follow.isPresent()) throw new AlreadyFollowedException();

        Follow newFollow = new Follow(fromMember, toMember);
        followRepository.save(newFollow);
    }

    public void unFollow(MemberContext memberContext, Long toMemberId) {
        String email = memberContext.getEmail();
        Member fromMember = findMemberService.findByEmailOrElseThrow(email);
        Member toMember = findMemberService.findByIdOrElseThrow(toMemberId);

        Follow follow = findFollowService.findByEachMemberOrElseThrow(fromMember, toMember);
        followRepository.delete(follow);
    }
}
