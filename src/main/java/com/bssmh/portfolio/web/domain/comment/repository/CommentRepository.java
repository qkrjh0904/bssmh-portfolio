package com.bssmh.portfolio.web.domain.comment.repository;
import com.bssmh.portfolio.db.entity.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentByPortfolioIdOrderByCreatedDateDesc(Long portfolioId);

}
