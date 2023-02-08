package com.bssmh.portfolio.web.domain.comment.controller.rs;

import com.bssmh.portfolio.db.entity.comment.Comment;
import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.web.config.security.context.MemberContext;
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

    public static FindCommentRs create(Comment comment, Member member) {
        FindCommentRs rs = new FindCommentRs();
        rs.writer = MemberDto.create(comment.getMember());
        rs.content = comment.getContent();
        rs.createdDate = comment.getCreatedDate().toString();
        rs.editable = editablePermission(rs.writer.getMemberId(), member);
        return rs;
    }
    private static boolean editablePermission(Long writerId, Member member) {
        if (member == null) return false;
        return member.getId().equals(writerId);
    }

}
