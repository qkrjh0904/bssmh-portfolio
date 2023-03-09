package com.bssmh.portfolio.web.domain.comment.controller.rs;

import com.bssmh.portfolio.db.entity.comment.Comment;
import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.db.entity.portfolio.Portfolio;
import com.bssmh.portfolio.web.domain.dto.MemberDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class FindCommentRs {

    @Schema(description = "작성자")
    private MemberDto writer;

    @Schema(description = "댓글 id")
    private Long commentId;

    @Schema(description = "부모 댓글 id")
    private Long parentId;

    @Schema(description = "자식 댓글")
    private List<FindCommentRs> children = new ArrayList<>();

    @Schema(description = "내용")
    private String content;

    @Schema(description = "생성일")
    private String createdDate;

    @Schema(description = "수정가능 여부")
    private Boolean editable;

    @Schema(description = "삭제가능 여부")
    private Boolean deletable;

    public static FindCommentRs create(Comment comment, Member member) {
        FindCommentRs rs = new FindCommentRs();
        rs.writer = MemberDto.create(comment.getMember());
        rs.commentId = comment.getId();
        rs.content = comment.getContent();
        rs.createdDate = comment.getCreatedDate().toString();
        rs.editable = getEditable(rs.writer.getMemberId(), member);
        rs.deletable = getDeletable(rs.writer.getMemberId(), member, comment.getPortfolio());

        for (Comment childComment: comment.getChildren()) {
            FindCommentRs childCommentRs = create(childComment, member);
            rs.children.add(childCommentRs);
        }

        return rs;
    }

    private static boolean getEditable(Long writerId, Member member) {
        if (Objects.isNull(member)) {
            return false;
        }
        return member.getId().equals(writerId);
    }

    private static boolean getDeletable(Long writerId, Member member, Portfolio portfolio) {
        if (Objects.isNull(member)){
            return false;
        }
        // 포트폴리오 작성자가 본인일 때
        if (Objects.equals(portfolio.getMember().getId(), member.getId())) {
            return true;
        }
        return member.getId().equals(writerId);
    }

}
