package com.bssmh.portfolio.web.domain.comment.service;

import com.bssmh.portfolio.db.entity.bookmark.CommentBookmark;
import com.bssmh.portfolio.db.entity.comment.Comment;
import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.web.config.security.context.MemberContext;
import com.bssmh.portfolio.web.domain.comment.controller.rq.BookmarkCommentRq;
import com.bssmh.portfolio.web.domain.comment.repository.CommentBookmarkRepository;
import com.bssmh.portfolio.web.domain.member.service.FindMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentBookmarkService {

    private final CommentBookmarkRepository commentBookmarkRepository;
    private final FindCommentService findCommentService;
    private final FindMemberService findMemberService;


    public void bookmarkComment(MemberContext memberContext, BookmarkCommentRq rq) {
        Member loginMember = findMemberService.findLoginMember(memberContext);
        Comment comment = findCommentService.findByIdOrElseThrow(rq.getCommentId());

        CommentBookmark commentBookmark = findByMemberAndCommentOrElseNull(loginMember, comment);
        // 북마크가 있으면 삭제
        if (Objects.nonNull(commentBookmark)) {
            commentBookmarkRepository.delete(commentBookmark);
            return;
        }
        // 북마크가 없으면 등록
        commentBookmark = CommentBookmark.create(comment, loginMember);
        commentBookmarkRepository.save(commentBookmark);
    }

    private CommentBookmark findByMemberAndCommentOrElseNull(Member member, Comment comment) {
        return commentBookmarkRepository.findByMemberAndComment(member, comment)
                .orElse(null);
    }

}
