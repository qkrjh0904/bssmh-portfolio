package com.bssmh.portfolio.web.domain.comment.service;

import com.bssmh.portfolio.db.entity.bookmark.CommentBookmark;
import com.bssmh.portfolio.db.entity.comment.Comment;
import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.web.domain.comment.repository.CommentBookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentBookmarkService {

    private final FindCommentBookmarkService findCommentBookmarkService;

    // repository
    private final CommentBookmarkRepository commentBookmarkRepository;

    public void toggleBookmarkComment(Member member, Comment comment) {

        CommentBookmark commentBookmark = findCommentBookmarkService.findByMemberAndCommentOrElseNull(member, comment);
        if (Objects.nonNull(commentBookmark)) {
            commentBookmarkRepository.delete(commentBookmark);
            return;
        }

        CommentBookmark newBookmark = CommentBookmark.create(comment, member);
        commentBookmarkRepository.save(newBookmark);
    }

}
