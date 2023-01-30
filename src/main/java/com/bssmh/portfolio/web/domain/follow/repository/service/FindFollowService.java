package com.bssmh.portfolio.web.domain.follow.repository.service;

import com.bssmh.portfolio.db.entity.follow.Follow;
import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.db.entity.portfolio.Portfolio;
import com.bssmh.portfolio.web.config.security.context.MemberContext;
import com.bssmh.portfolio.web.domain.follow.repository.FollowRepository;
import com.bssmh.portfolio.web.domain.member.controller.rs.FindMemberSelfRs;
import com.bssmh.portfolio.web.domain.member.controller.rs.FindOtherMemberRs;
import com.bssmh.portfolio.web.domain.member.repository.MemberRepository;
import com.bssmh.portfolio.web.domain.member.service.FindMemberService;
import com.bssmh.portfolio.web.domain.portfolio.controller.rs.FindPortfolioListRs;
import com.bssmh.portfolio.web.endpoint.ListResponse;
import com.bssmh.portfolio.web.endpoint.PagedResponse;
import com.bssmh.portfolio.web.endpoint.Pagination;
import com.bssmh.portfolio.web.exception.NoSuchMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FindFollowService {

    // service
    private final FindMemberService findMemberService;

    // repository
    private final FollowRepository followRepository;

    public ListResponse<FindOtherMemberRs> findMyFollower(MemberContext memberContext) {
        String email = memberContext.getEmail();
        Member member = findMemberService.findByEmailOrElseThrow(email);

        List<Follow> myFollowerList = member.getToMemberList();
        List<FindOtherMemberRs> myFollowerRsList = myFollowerList.stream()
                .map(follow -> FindOtherMemberRs.create(follow.getFromMember()))
                .collect(Collectors.toList());

        return ListResponse.create(myFollowerRsList);
    }

    public ListResponse<FindOtherMemberRs> findMyFollowing(MemberContext memberContext) {
        String email = memberContext.getEmail();
        Member member = findMemberService.findByEmailOrElseThrow(email);

        List<Follow> myFollowingList = member.getFromMemberList();
        List<FindOtherMemberRs> myFollowingRsList = myFollowingList.stream()
                .map(follow -> FindOtherMemberRs.create(follow.getToMember()))
                .collect(Collectors.toList());

        return ListResponse.create(myFollowingRsList);
    }

    public Optional<Follow> findByEachMemberId(Member fromMember, Member toMember) {
        return followRepository.findByFromMemberAndToMember(fromMember, toMember);
    }

    public Follow findByEachMemberIdOrElseThrow(Member fromMember, Member toMember) {
        return followRepository.findByFromMemberAndToMember(fromMember, toMember)
                .orElseThrow(NoSuchMemberException::new);
    }

}
