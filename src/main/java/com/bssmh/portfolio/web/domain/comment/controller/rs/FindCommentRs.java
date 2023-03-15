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
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class FindCommentRs {

    @Schema(description = "작성자")
    private MemberDto writer;

    @Schema(description = "댓글 id")
    private Long commentId;

    @Schema(description = "내용")
    private String content;

    @Schema(description = "생성일")
    private String createdDate;

    @Schema(description = "수정가능 여부")
    private Boolean editable;

    @Schema(description = "삭제가능 여부")
    private Boolean deletable;

    @Schema(description = "좋아요수")
    private Long bookmarks;

    @Schema(description = "좋아요 여부")
    private Boolean bookmarkYn;

    @Schema(description = "부모 댓글 id")
    private Long parentId;

    @Schema(description = "자식 댓글")
    private List<FindCommentRs> replyList = new ArrayList<>();

    public static FindCommentRs create(Comment comment, Member loginMember, Set<Long> bookmarkedCommentIdSet) {
        FindCommentRs rs = new FindCommentRs();
        rs.writer = MemberDto.create(comment.getMember());
        rs.commentId = comment.getId();
        rs.content = comment.getContent();
        rs.createdDate = comment.getCreatedDate().toString();
        rs.editable = getEditable(rs.writer.getMemberId(), loginMember);
        rs.deletable = getDeletable(rs.writer.getMemberId(), loginMember, comment.getPortfolio());
        rs.bookmarks = getBookmarks(comment);
        rs.bookmarkYn = getBookmarkYn(comment, bookmarkedCommentIdSet);
        rs.parentId = getParentId(comment);
        rs.replyList = getReplyList(comment, loginMember, bookmarkedCommentIdSet);
        return rs;
    }

    private static List<FindCommentRs> getReplyList(Comment comment, Member loginMember, Set<Long> bookmarkedCommentIdSet) {
        List<Comment> children = comment.getChildren();
        return children.stream()
                .map(child -> FindCommentRs.create(child, loginMember, bookmarkedCommentIdSet))
                .collect(Collectors.toList());
    }

    private static Long getParentId(Comment comment) {
        Comment parent = comment.getParent();
        if (Objects.isNull(parent)) {
            return null;
        }
        return parent.getId();
    }

    private static boolean getEditable(Long writerId, Member loginMember) {
        if (Objects.isNull(loginMember)) {
            return false;
        }
        return loginMember.getId().equals(writerId);
    }

    private static boolean getDeletable(Long writerId, Member loginMember, Portfolio portfolio) {
        if (Objects.isNull(loginMember)) {
            return false;
        }
        // 포트폴리오 작성자가 본인일 때
        if (Objects.equals(portfolio.getMember().getId(), loginMember.getId())) {
            return true;
        }
        return loginMember.getId().equals(writerId);
    }

    private static Long getBookmarks(Comment comment) {
        return (long) comment.getBookmarkList().size();
    }

    private static Boolean getBookmarkYn(Comment comment, Set<Long> bookmarkedCommentIdSet) {
        return bookmarkedCommentIdSet.contains(comment.getId());
    }

}
