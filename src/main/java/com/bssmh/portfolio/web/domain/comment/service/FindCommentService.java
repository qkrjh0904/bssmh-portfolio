package com.bssmh.portfolio.web.domain.comment.service;
import com.bssmh.portfolio.db.entity.comment.Comment;
import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.web.config.security.context.MemberContext;
import com.bssmh.portfolio.web.domain.comment.controller.rs.FindCommentRs;
import com.bssmh.portfolio.web.domain.comment.repository.CommentRepository;
import com.bssmh.portfolio.web.domain.member.service.FindMemberService;
import com.bssmh.portfolio.web.endpoint.ListResponse;
import com.bssmh.portfolio.web.exception.NoSuchCommentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FindCommentService {

    // repository
    private final CommentRepository commentRepository;

    // service
    private final FindMemberService findMemberService;

    public ListResponse<FindCommentRs> findCommentByPortfolioId(MemberContext memberContext, Long portfolioId) {
        List<Comment> commentList = commentRepository.findCommentByPortfolioId(portfolioId);
        if (memberContext == null) {
            return findCommentWithoutMemberId(commentList, portfolioId);
        } else {
            String email = memberContext.getEmail();
            String registrationId = memberContext.getRegistrationId();
            Member member = findMemberService.findByEmailAndRegistrationIdOrElseThrow(email, registrationId);
            return findCommentWithMemberId(commentList, portfolioId, member.getId());
        }
    }

    private ListResponse<FindCommentRs> findCommentWithMemberId(List<Comment> commentList, Long portfolioId, Long memberId) {
        List<FindCommentRs> commentRsList = commentList.stream()
                .map(comment -> FindCommentRs.create(comment, memberId))
                .collect(Collectors.toList());
        return ListResponse.create(commentRsList);
    }
    private ListResponse<FindCommentRs> findCommentWithoutMemberId(List<Comment> commentList, Long portfolioId) {
        List<FindCommentRs> commentRsList = commentList.stream()
                .map(comment -> FindCommentRs.create(comment, null))
                .collect(Collectors.toList());
        return ListResponse.create(commentRsList);
    }

    public Comment findByIdOrElseThrow(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(NoSuchCommentException::new);
    }

}
