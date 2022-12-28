package com.bssmh.portfolio.web.domain.comment.controller.rq;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateCommentRq {

    @NotNull
    @Schema(description = "댓글 id")
    private Long commentId;

    @NotNull
    @Schema(description = "댓글 내용")
    private String content;

}
