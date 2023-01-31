package com.bssmh.portfolio.web.domain.follow.repository.controller.rq;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class UnFollowMemberRq {

    @NotNull
    @Schema(description = "팔로우 취소할 대상의 멤버 id")
    private Long memberId;
}
