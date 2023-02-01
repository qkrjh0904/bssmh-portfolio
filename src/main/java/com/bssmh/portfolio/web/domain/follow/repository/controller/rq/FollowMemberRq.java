package com.bssmh.portfolio.web.domain.follow.repository.controller.rq;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class FollowMemberRq {

    @NotNull
    @Schema(description = "팔로우할 대상의 멤버 id")
    private Long memberId;
}
