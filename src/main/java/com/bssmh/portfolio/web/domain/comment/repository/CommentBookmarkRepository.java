package com.bssmh.portfolio.web.domain.comment.repository;

import com.bssmh.portfolio.db.entity.bookmark.CommentBookmark;
import com.bssmh.portfolio.db.entity.comment.Comment;
import com.bssmh.portfolio.db.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentBookmarkRepository extends JpaRepository<CommentBookmark, Long> {

    Optional<CommentBookmark> findByMemberAndComment(Member member, Comment comment);

    List<CommentBookmark> findByMember(Member member);

}