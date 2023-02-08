package com.bssmh.portfolio.web.domain.follow.repository.service;

import com.bssmh.portfolio.db.entity.follow.Follow;
import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.web.config.security.context.MemberContext;
import com.bssmh.portfolio.web.domain.follow.repository.FollowRepository;
import com.bssmh.portfolio.web.domain.follow.repository.controller.rq.FollowMemberRq;
import com.bssmh.portfolio.web.domain.follow.repository.controller.rq.UnFollowMemberRq;
import com.bssmh.portfolio.web.domain.member.service.FindMemberService;
import com.bssmh.portfolio.web.exception.SelfFollowNotAllowedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowService {

    // service
    private final FindFollowService findFollowService;
    private final FindMemberService findMemberService;

    // repository
    private final FollowRepository followRepository;

    public void followMember(MemberContext memberContext, FollowMemberRq rq) {
        Member fromMember = findMemberService.getLoginMember(memberContext);
        Member toMember = findMemberService.findByIdOrElseThrow(rq.getMemberId());
        selfFollowCheck(fromMember, toMember);
        duplicateFollowCheck(fromMember, toMember);

        Follow newFollow = Follow.create(fromMember, toMember);
        followRepository.save(newFollow);
    }

    private void selfFollowCheck(Member fromMember, Member toMember) {
        if (fromMember.getId().equals(toMember.getId())) {
            throw new SelfFollowNotAllowedException();
        }
    }

    private void duplicateFollowCheck(Member fromMember, Member toMember) {
        findFollowService.findByEachMemberIfPresentThrow(fromMember, toMember);
    }

    public void unFollowMember(MemberContext memberContext, UnFollowMemberRq rq) {
        Member fromMember = findMemberService.getLoginMember(memberContext);
        Member toMember = findMemberService.findByIdOrElseThrow(rq.getMemberId());

        Follow follow = findFollowService.findByEachMemberOrElseThrow(fromMember, toMember);
        followRepository.delete(follow);
    }
}
