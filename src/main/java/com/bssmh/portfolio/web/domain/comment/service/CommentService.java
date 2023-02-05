package com.bssmh.portfolio.web.domain.comment.service;

import com.bssmh.portfolio.db.entity.comment.Comment;
import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.db.entity.portfolio.Portfolio;
import com.bssmh.portfolio.web.config.security.context.MemberContext;
import com.bssmh.portfolio.web.domain.comment.controller.rq.SaveCommentRq;
import com.bssmh.portfolio.web.domain.comment.repository.CommentRepository;
import com.bssmh.portfolio.web.domain.member.service.FindMemberService;
import com.bssmh.portfolio.web.domain.portfolio.service.FindPortfolioService;
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

    // repository
    private final CommentRepository commentRepository;
    public void saveComment(MemberContext memberContext, SaveCommentRq rq) {
        String email = memberContext.getEmail();
        Member member = findMemberService.findByEmailAndRegistrationIdOrElseNull(email, memberContext.getRegistrationId());
        if(Objects.isNull(member)){
            throw new NoSuchMemberException();
        }

        Portfolio portfolio = findPortfolioService.findByIdOrElseThrow(rq.getPortfolioId());

        Comment comment = Comment.create(
                rq.getContent(),
                portfolio,
                member);
        commentRepository.save(comment);
    }
}
