package com.bssmh.portfolio.web.domain.comment.repository;

import com.bssmh.portfolio.db.entity.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {
}
