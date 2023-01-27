package com.bssmh.portfolio.web.domain.follow.repository.controller;

import com.bssmh.portfolio.web.config.security.context.MemberContext;
import com.bssmh.portfolio.web.domain.follow.repository.controller.rq.FollowMemberRq;
import com.bssmh.portfolio.web.domain.follow.repository.service.FollowService;
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

    private final FollowService followService;

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
}
