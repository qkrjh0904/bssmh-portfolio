package com.bssmh.portfolio.web.domain.comment.controller.rq;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

@Data
public class SaveCommentRq {

    @NotNull
    @Schema(description = "포트폴리오 id")
    private Long portfolioId;

    @NotNull
    @Schema(description = "댓글 내용")
    private String content;

    @Nullable
    @Schema(description = "부모 댓글")
    private Long parentId;

}
