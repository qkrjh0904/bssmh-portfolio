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
        String email = null;
        String registrationId = null;
        // 로그인한 사용자라면
        if (memberContext != null) {
            email = memberContext.getEmail();
            registrationId = memberContext.getRegistrationId();
        }
        Member member = findMemberService.findByEmailAndRegistrationIdOrElseNull(email, registrationId);
        List<FindCommentRs> commentRsList = commentList.stream()
                .map(comment -> FindCommentRs.create(comment, member))
                .collect(Collectors.toList());
        return ListResponse.create(commentRsList);
    }

    public Comment findByIdOrElseThrow(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(NoSuchCommentException::new);
    }

}
