package com.bssmh.portfolio.web.domain.comment.controller.rs;

import com.bssmh.portfolio.web.domain.dto.MemberDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class FindCommentRs {

    @Schema(description = "작성자")
    private MemberDto writer;

    @Schema(description = "내용")
    private String content;

    @Schema(description = "생성일")
    private String createdDate;

    @Schema(description = "수정가능 여부")
    private Boolean editable;

}
