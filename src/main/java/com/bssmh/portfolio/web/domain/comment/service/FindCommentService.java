package com.bssmh.portfolio.web.domain.comment.service;

import com.bssmh.portfolio.db.entity.comment.Comment;
import com.bssmh.portfolio.db.entity.follow.Follow;
import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.web.domain.comment.controller.rs.FindCommentRs;
import com.bssmh.portfolio.web.domain.comment.repository.CommentRepository;
import com.bssmh.portfolio.web.domain.member.controller.rs.FindOtherMemberRs;
import com.bssmh.portfolio.web.endpoint.ListResponse;
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

//    private ListResponse<FindOtherMemberRs> toFollowingRsList(Member member) {
//        List<Follow> followingList = member.getFromMemberList();
//        List<FindOtherMemberRs> followingRsList = followingList.stream()
//                .map(follow -> FindOtherMemberRs.create(follow.getToMember()))
//                .collect(Collectors.toList());
//        return ListResponse.create(followingRsList);
//    }
    public ListResponse<FindCommentRs> findCommentByPortfolioId(Long portfolioId) {
        List<Comment> commentList = commentRepository.findCommentByPortfolioId(portfolioId);
        List<FindCommentRs> commentRsList = commentList.stream()
                .map(comment -> FindCommentRs.create(comment))
                .collect(Collectors.toList());
        return ListResponse.create(commentRsList);
    }
}
