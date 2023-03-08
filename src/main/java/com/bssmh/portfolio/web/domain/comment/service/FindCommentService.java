package com.bssmh.portfolio.web.domain.comment.service;

import com.bssmh.portfolio.db.entity.comment.Comment;
import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.db.entity.portfolio.Portfolio;
import com.bssmh.portfolio.web.config.security.context.MemberContext;
import com.bssmh.portfolio.web.domain.comment.controller.rs.FindCommentRs;
import com.bssmh.portfolio.web.domain.comment.repository.CommentRepository;
import com.bssmh.portfolio.web.domain.member.service.FindMemberService;
import com.bssmh.portfolio.web.domain.portfolio.service.FindPortfolioService;
import com.bssmh.portfolio.web.endpoint.ListResponse;
import com.bssmh.portfolio.web.exception.NoSuchCommentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FindCommentService {

    // repository
    private final CommentRepository commentRepository;

    // service
    private final FindMemberService findMemberService;
    private final FindPortfolioService findPortfolioService;

    public ListResponse<FindCommentRs> findCommentByPortfolioId(MemberContext memberContext, Long portfolioId) {
        Portfolio portfolio = findPortfolioService.findByIdOrElseThrow(portfolioId);
        List<Comment> commentList = commentRepository.findCommentAllByPortfolio(portfolio);
        Member member = findMemberService.findLoginMember(memberContext);

        List<FindCommentRs> commentRsList = new ArrayList<>();
        Map<Long, FindCommentRs> commentMap = new HashMap<>();

        for (Comment comment: commentList) {
            FindCommentRs commentRs = FindCommentRs.create(comment, member);
            if (comment.getParent() != null) {
                commentRs.setParentId(comment.getParent().getId());
            }
            commentMap.put(commentRs.getCommentId(), commentRs);
            // 만약 자식 댓글이면
            if (comment.getParent() != null) {
                // 부모 댓글에 자식 댓글 추가
                commentMap.get(comment.getParent().getId()).getChildren().add(commentRs);
                continue;
            }
            commentRsList.add(commentRs);
        }

        return ListResponse.create(commentRsList);
    }

    public Comment findByIdOrElseThrow(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(NoSuchCommentException::new);
    }

}
