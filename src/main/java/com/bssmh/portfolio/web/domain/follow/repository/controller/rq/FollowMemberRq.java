package com.bssmh.portfolio.web.domain.follow.repository.controller.rq;

import com.bssmh.portfolio.db.entity.follow.Follow;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class FollowMemberRq {

    @NotNull
    @Schema(description = "멤버 id")
    private Long memberId;
}
