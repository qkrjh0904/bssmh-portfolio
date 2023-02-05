package com.bssmh.portfolio.web.domain.comment.service;
import com.bssmh.portfolio.db.entity.comment.Comment;
import com.bssmh.portfolio.db.entity.portfolio.Portfolio;
import com.bssmh.portfolio.web.domain.comment.controller.rs.FindCommentRs;
import com.bssmh.portfolio.web.domain.comment.repository.CommentRepository;
import com.bssmh.portfolio.web.endpoint.ListResponse;
import com.bssmh.portfolio.web.exception.NoSuchCommentException;
import com.bssmh.portfolio.web.exception.NoSuchPortfolioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FindCommentService {

    private final CommentRepository commentRepository;

    public ListResponse<FindCommentRs> findCommentByPortfolioId(Long portfolioId) {
        List<Comment> commentList = commentRepository.findCommentByPortfolioId(portfolioId);
        List<FindCommentRs> commentRsList = commentList.stream()
                .map(FindCommentRs::create)
                .collect(Collectors.toList());
        return ListResponse.create(commentRsList);
    }

    public Comment findByIdOrElseThrow(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(NoSuchCommentException::new);
    }

}
