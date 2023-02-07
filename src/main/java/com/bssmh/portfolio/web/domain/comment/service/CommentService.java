package com.bssmh.portfolio.web.domain.comment.service;
import com.bssmh.portfolio.db.entity.comment.Comment;
import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.db.entity.portfolio.Portfolio;
import com.bssmh.portfolio.web.config.security.context.MemberContext;
import com.bssmh.portfolio.web.domain.comment.controller.rq.DeleteCommentRq;
import com.bssmh.portfolio.web.domain.comment.controller.rq.SaveCommentRq;
import com.bssmh.portfolio.web.domain.comment.controller.rq.UpdateCommentRq;
import com.bssmh.portfolio.web.domain.comment.repository.CommentRepository;
import com.bssmh.portfolio.web.domain.member.service.FindMemberService;
import com.bssmh.portfolio.web.domain.portfolio.service.FindPortfolioService;
import com.bssmh.portfolio.web.exception.DoNotHavePermissionToModifyCommentException;
import com.bssmh.portfolio.web.exception.NoSuchMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    // service
    private final FindMemberService findMemberService;

    private final FindPortfolioService findPortfolioService;

    private final FindCommentService findCommentService;

    // repository
    private final CommentRepository commentRepository;
    public void saveComment(MemberContext memberContext, SaveCommentRq rq) {
        String email = memberContext.getEmail();
        Member member = findMemberService.findByEmailAndRegistrationIdOrElseThrow(email, memberContext.getRegistrationId());
        Portfolio portfolio = findPortfolioService.findByIdOrElseThrow(rq.getPortfolioId());
        Comment comment = Comment.create(
                rq.getContent(),
                portfolio,
                member);
        commentRepository.save(comment);
    }

    public void deleteComment(MemberContext memberContext, DeleteCommentRq rq) {
        String email = memberContext.getEmail();
        String registrationId = memberContext.getRegistrationId();
        Member member = findMemberService.findByEmailAndRegistrationIdOrElseThrow(email, registrationId);
        Long commentId = rq.getCommentId();
        Comment comment = findCommentService.findByIdOrElseThrow(commentId);
        commentPermissionCheck(comment, member);
        commentRepository.delete(comment);
    }

    public void updateComment(MemberContext memberContext, UpdateCommentRq rq) {
        String email = memberContext.getEmail();
        String registrationId = memberContext.getRegistrationId();
        Member member = findMemberService.findByEmailAndRegistrationIdOrElseThrow(email, registrationId);
        Long commentId = rq.getCommentId();
        Comment comment = findCommentService.findByIdOrElseThrow(commentId);
        commentPermissionCheck(comment, member);
        comment.update(rq.getContent());
    }

    private void commentPermissionCheck(Comment comment, Member member) {
        Long writerId = comment.getMember().getId();
        Long memberId = member.getId();
        if (writerId.equals(memberId)){
            return;
        }
        throw new DoNotHavePermissionToModifyCommentException();
    }

}
