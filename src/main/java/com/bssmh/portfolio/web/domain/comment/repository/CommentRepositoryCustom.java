package com.bssmh.portfolio.web.domain.comment.repository;

import com.bssmh.portfolio.db.entity.comment.Comment;

import java.util.List;

public interface CommentRepositoryCustom {
    List<Comment> findCommentByPortfolioId(Number portfolioId);

}
