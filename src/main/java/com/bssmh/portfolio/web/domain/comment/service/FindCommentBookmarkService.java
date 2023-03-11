package com.bssmh.portfolio.web.domain.comment.service;

import com.bssmh.portfolio.db.entity.bookmark.CommentBookmark;
import com.bssmh.portfolio.db.entity.comment.Comment;
import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.web.domain.comment.repository.CommentBookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FindCommentBookmarkService {

    private final CommentBookmarkRepository commentBookmarkRepository;

    public CommentBookmark findByMemberAndCommentOrElseNull(Member member, Comment comment) {
        return commentBookmarkRepository.findByMemberAndComment(member, comment)
                .orElse(null);
    }

    public List<CommentBookmark> findByMember(Member member) {
        return commentBookmarkRepository.findByMember(member);
    }

}
