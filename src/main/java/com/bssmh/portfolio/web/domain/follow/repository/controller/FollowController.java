package com.bssmh.portfolio.web.domain.follow.repository.controller;

import com.bssmh.portfolio.web.config.security.context.MemberContext;
import com.bssmh.portfolio.web.domain.follow.repository.controller.rq.FollowMemberRq;
import com.bssmh.portfolio.web.domain.follow.repository.controller.rq.UnFollowMemberRq;
import com.bssmh.portfolio.web.domain.follow.repository.service.FindFollowService;
import com.bssmh.portfolio.web.domain.follow.repository.service.FollowService;
import com.bssmh.portfolio.web.domain.member.controller.rs.FindFollowMemberRs;
import com.bssmh.portfolio.web.endpoint.ListResponse;
import com.bssmh.portfolio.web.path.ApiPath;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "팔로우")
@RestController
@RequiredArgsConstructor
public class FollowController {

    // service
    private final FollowService followService;
    private final FindFollowService findFollowService;

    @Operation(summary = "멤버 팔로우")
    @PostMapping(ApiPath.FOLLOW)
    public void followMember(@AuthenticationPrincipal MemberContext memberContext,
                             @RequestBody FollowMemberRq rq) {
        followService.followMember(memberContext, rq);
    }

    @Operation(summary = "멤버 팔로우 취소")
    @DeleteMapping(ApiPath.UNFOLLOW)
    public void unFollowMember(@AuthenticationPrincipal MemberContext memberContext,
                               @RequestBody UnFollowMemberRq rq) {
        followService.unFollowMember(memberContext, rq);
    }

    @Operation(summary = "내 팔로워 목록")
    @GetMapping(ApiPath.FOLLOWER_SELF)
    public ListResponse<FindFollowMemberRs> findMyFollower(@AuthenticationPrincipal MemberContext memberContext) {
        return findFollowService.findMyFollower(memberContext);
    }

    @Operation(summary = "내 팔로잉 목록")
    @GetMapping(ApiPath.FOLLOWING_SELF)
    public ListResponse<FindFollowMemberRs> findMyFollowing(@AuthenticationPrincipal MemberContext memberContext) {
        return findFollowService.findMyFollowing(memberContext);
    }

    @Operation(summary = "다른 멤버 팔로워 목록")
    @GetMapping(ApiPath.FOLLOWER_MEMBER_ID)
    public ListResponse<FindFollowMemberRs> findMemberFollower(@PathVariable("member-id") Long memberId) {
        return findFollowService.findMemberFollower(memberId);
    }

    @Operation(summary = "다른 멤버 팔로잉 목록")
    @GetMapping(ApiPath.FOLLOWING_MEMBER_ID)
    public ListResponse<FindFollowMemberRs> findMemberFollowing(@PathVariable("member-id") Long memberId) {
        return findFollowService.findMemberFollowing(memberId);
    }

}
