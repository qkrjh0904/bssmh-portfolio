package com.bssmh.portfolio.web.domain.comment.service;

import com.bssmh.portfolio.db.entity.bookmark.CommentBookmark;
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
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FindCommentService {

    // repository
    private final CommentRepository commentRepository;

    // service
    private final FindMemberService findMemberService;
    private final FindPortfolioService findPortfolioService;
    private final CommentBookmarkService findCommentBookmarkService;

    public ListResponse<FindCommentRs> findCommentByPortfolioId(MemberContext memberContext, Long portfolioId) {
        Portfolio portfolio = findPortfolioService.findByIdOrElseThrow(portfolioId);
        List<Comment> commentList = commentRepository.findParentCommentByPortfolio(portfolio);
        Member loginMember = findMemberService.findLoginMember(memberContext);
        Set<Long> bookmarkedCommentIdSet = getMyBookmarkedCommentIdSet(memberContext);

        List<FindCommentRs> findCommentRsList = commentList.stream()
                .map(comment -> FindCommentRs.create(comment, loginMember, bookmarkedCommentIdSet))
                .collect(Collectors.toList());
        return ListResponse.create(findCommentRsList);
    }

    public Comment findByIdOrElseThrow(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(NoSuchCommentException::new);
    }

    private Set<Long> getMyBookmarkedCommentIdSet(MemberContext memberContext) {
        if (Objects.isNull(memberContext)) {
            return new HashSet<>();
        }

        Member loginMember = findMemberService.findLoginMember(memberContext);
        return findCommentBookmarkService.findByMember(loginMember).stream()
                .map(CommentBookmark::getComment)
                .map(Comment::getId)
                .collect(Collectors.toSet());
    }

}
