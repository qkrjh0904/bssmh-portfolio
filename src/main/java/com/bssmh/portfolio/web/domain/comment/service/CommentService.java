package com.bssmh.portfolio.web.domain.comment.service;

import com.bssmh.portfolio.db.entity.comment.Comment;
import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.db.entity.portfolio.Portfolio;
import com.bssmh.portfolio.web.config.security.context.MemberContext;
import com.bssmh.portfolio.web.domain.comment.controller.rq.BookmarkCommentRq;
import com.bssmh.portfolio.web.domain.comment.controller.rq.DeleteCommentRq;
import com.bssmh.portfolio.web.domain.comment.controller.rq.SaveCommentRq;
import com.bssmh.portfolio.web.domain.comment.controller.rq.UpdateCommentRq;
import com.bssmh.portfolio.web.domain.comment.repository.CommentRepository;
import com.bssmh.portfolio.web.domain.member.service.FindMemberService;
import com.bssmh.portfolio.web.domain.portfolio.service.FindPortfolioService;
import com.bssmh.portfolio.web.exception.DepthLimitExceededException;
import com.bssmh.portfolio.web.exception.DoNotHavePermissionToDeleteCommentException;
import com.bssmh.portfolio.web.exception.DoNotHavePermissionToModifyCommentException;
import com.bssmh.portfolio.web.exception.NotMatchedParentChildPortfolioIdException;
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

    private final CommentBookmarkService commentBookmarkService;

    // repository
    private final CommentRepository commentRepository;

    public void saveComment(MemberContext memberContext, SaveCommentRq rq) {
        Member member = findMemberService.findLoginMember(memberContext);
        Portfolio portfolio = findPortfolioService.findByIdOrElseThrow(rq.getPortfolioId());

        Long parentId = rq.getParentId();
        Comment parent = getCommentParent(parentId);

        // 자식 댓글인 경우 예외처리
        if (Objects.nonNull(parentId)) {

            Long childPortfolioId = rq.getPortfolioId();
            Long parentPortfolioId = parent.getPortfolio().getId();
            // 부모 댓글의 포트폴리오 번호와 자식 댓글의 포트폴리오 번호가 같지 않다면
            if (!parentPortfolioId.equals(childPortfolioId)) {
                throw new NotMatchedParentChildPortfolioIdException();
            }

            // 댓글의 깊이가 제한을 초과했을 때
            if (Objects.nonNull(parent.getParent())) {
                throw new DepthLimitExceededException();
            }

        }

        Comment comment = Comment.create(
                rq.getContent(),
                portfolio,
                member,
                parent);
        commentRepository.save(comment);
    }

    public void deleteComment(MemberContext memberContext, DeleteCommentRq rq) {
        Member member = findMemberService.findLoginMember(memberContext);
        Long commentId = rq.getCommentId();
        Comment comment = findCommentService.findByIdOrElseThrow(commentId);
        commentDeletePermissionCheck(comment, member);
        commentRepository.delete(comment);
    }

    public void updateComment(MemberContext memberContext, UpdateCommentRq rq) {
        Member member = findMemberService.findLoginMember(memberContext);
        Long commentId = rq.getCommentId();
        Comment comment = findCommentService.findByIdOrElseThrow(commentId);
        commentModifyPermissionCheck(comment, member);
        comment.update(rq.getContent());
    }

    private void commentModifyPermissionCheck(Comment comment, Member member) {
        Long writerId = comment.getMember().getId();
        Long memberId = member.getId();
        if (writerId.equals(memberId)) {
            return;
        }
        throw new DoNotHavePermissionToModifyCommentException();
    }

    private void commentDeletePermissionCheck(Comment comment, Member member) {
        Long writerId = comment.getMember().getId();
        Long memberId = member.getId();
        Long portfolioWriterId = comment.getPortfolio().getMember().getId();
        if (portfolioWriterId.equals(memberId) || writerId.equals(memberId)) {
            return;
        }
        throw new DoNotHavePermissionToDeleteCommentException();
    }

    private Comment getCommentParent(Long parentId) {
        // 자식 댓글의 경우
        if (Objects.nonNull(parentId))  {
            return findCommentService.findByIdOrElseThrow(parentId);
        }
        return null;
    }

    public void bookmarkPortfolio(MemberContext memberContext, BookmarkCommentRq rq) {
        Member member = findMemberService.findLoginMember(memberContext);
        Comment comment = findCommentService.findByIdOrElseThrow(rq.getCommentId());
        commentBookmarkService.toggleBookmarkComment(member, comment);
    }
}
