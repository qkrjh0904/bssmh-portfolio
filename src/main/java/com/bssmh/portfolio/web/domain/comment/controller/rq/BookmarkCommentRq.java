package com.bssmh.portfolio.web.domain.comment.controller.rq;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class BookmarkCommentRq {

    @NotNull
    @Schema(description = "댓글 id")
    private Long commentId;

}
