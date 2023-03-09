package com.bssmh.portfolio.web.domain.comment.repository;

import com.bssmh.portfolio.db.entity.comment.Comment;
import com.bssmh.portfolio.db.entity.portfolio.Portfolio;

import java.util.List;

public interface CommentRepositoryCustom {

    List<Comment> findParentCommentByPortfolio(Portfolio portfolio);

}
