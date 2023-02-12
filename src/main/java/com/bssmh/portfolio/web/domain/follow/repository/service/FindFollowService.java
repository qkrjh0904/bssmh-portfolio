package com.bssmh.portfolio.web.domain.follow.repository.service;

import com.bssmh.portfolio.db.entity.follow.Follow;
import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.web.config.security.context.MemberContext;
import com.bssmh.portfolio.web.domain.follow.repository.FollowRepository;
import com.bssmh.portfolio.web.domain.member.controller.rs.FindOtherMemberRs;
import com.bssmh.portfolio.web.domain.member.service.FindMemberService;
import com.bssmh.portfolio.web.endpoint.ListResponse;
import com.bssmh.portfolio.web.exception.AlreadyFollowedException;
import com.bssmh.portfolio.web.exception.NoSuchMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FindFollowService {

    // service
    private final FindMemberService findMemberService;

    // repository
    private final FollowRepository followRepository;

    public void findByEachMemberIfPresentThrow(Member fromMember, Member toMember) {
        followRepository.findByFromMemberAndToMember(fromMember, toMember)
                .ifPresent((follow) -> {throw new AlreadyFollowedException();});
    }

    public Follow findByEachMemberOrElseThrow(Member fromMember, Member toMember) {
        return followRepository.findByFromMemberAndToMember(fromMember, toMember)
                .orElseThrow(NoSuchMemberException::new);
    }

    public Follow findByEachMemberOrElseNull(Member fromMember, Member toMember) {
        return followRepository.findByFromMemberAndToMember(fromMember, toMember)
                .orElse(null);
    }

    public ListResponse<FindOtherMemberRs> findMyFollower(MemberContext memberContext) {
        Member member = findMemberService.findLoginMember(memberContext);
        return toFollowerRsList(member);
    }

    public ListResponse<FindOtherMemberRs> findMyFollowing(MemberContext memberContext) {
        Member member = findMemberService.findLoginMember(memberContext);
        return toFollowingRsList(member);
    }

    public ListResponse<FindOtherMemberRs> findMemberFollower(Long memberId) {
        Member member = findMemberService.findByIdOrElseThrow(memberId);
        return toFollowerRsList(member);
    }

    public ListResponse<FindOtherMemberRs> findMemberFollowing(Long memberId) {
        Member member = findMemberService.findByIdOrElseThrow(memberId);
        return toFollowingRsList(member);
    }

    private ListResponse<FindOtherMemberRs> toFollowingRsList(Member member) {
        List<Follow> followingList = member.getFromMemberList();
        List<FindOtherMemberRs> followingRsList = followingList.stream()
                .map(follow -> FindOtherMemberRs.create(follow.getToMember()))
                .collect(Collectors.toList());
        return ListResponse.create(followingRsList);
    }

    private ListResponse<FindOtherMemberRs> toFollowerRsList(Member member) {
        List<Follow> followerList = member.getToMemberList();
        List<FindOtherMemberRs> followerRsList = followerList.stream()
                .map(follow -> FindOtherMemberRs.create(follow.getFromMember()))
                .collect(Collectors.toList());
        return ListResponse.create(followerRsList);
    }

}
