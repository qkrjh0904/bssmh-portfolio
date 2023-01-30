package com.bssmh.portfolio.web.domain.follow.repository.controller;

import com.bssmh.portfolio.web.config.security.context.MemberContext;
import com.bssmh.portfolio.web.domain.follow.repository.controller.rq.FollowMemberRq;
import com.bssmh.portfolio.web.domain.follow.repository.service.FindFollowService;
import com.bssmh.portfolio.web.domain.follow.repository.service.FollowService;
import com.bssmh.portfolio.web.domain.member.controller.rs.FindOtherMemberRs;
import com.bssmh.portfolio.web.endpoint.ListResponse;
import com.bssmh.portfolio.web.path.ApiPath;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "팔로우")
@RestController
@RequiredArgsConstructor
public class FollowController {

    // service
    private final FollowService followService;
    private final FindFollowService findFollowService;

    @Operation(summary = "멤버 팔로우")
    @PostMapping(ApiPath.FOLLOW)
    public void follow(@AuthenticationPrincipal MemberContext memberContext,
                       @RequestBody FollowMemberRq rq) {
        followService.follow(memberContext, rq);
    }

    @Operation(summary = "멤버 팔로우 취소")
    @DeleteMapping(ApiPath.UNFOLLOW)
    public void unFollow(@AuthenticationPrincipal MemberContext memberContext,
                         @PathVariable("member-id") Long memberId) {
        followService.unFollow(memberContext, memberId);
    }

    @Operation(summary = "내 팔로워 목록")
    @GetMapping(ApiPath.FOLLOWER)
    public ListResponse<FindOtherMemberRs> follower(@AuthenticationPrincipal MemberContext memberContext) {
        return findFollowService.findMyFollower(memberContext);
    }

    @Operation(summary = "내 팔로잉 목록")
    @GetMapping(ApiPath.FOLLOWING)
    public ListResponse<FindOtherMemberRs> following(@AuthenticationPrincipal MemberContext memberContext) {
        return findFollowService.findMyFollowing(memberContext);
    }

}
