package com.bssmh.portfolio.web.domain.comment.controller;

import com.bssmh.portfolio.web.domain.comment.controller.rq.DeleteCommentRq;
import com.bssmh.portfolio.web.domain.comment.controller.rq.SaveCommentRq;
import com.bssmh.portfolio.web.domain.comment.controller.rq.UpdateCommentRq;
import com.bssmh.portfolio.web.domain.comment.controller.rs.FindCommentRs;
import com.bssmh.portfolio.web.path.ApiPath;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "댓글")
@RestController
@RequiredArgsConstructor
public class CommentController {


    @Operation(summary = "댓글 리스트 조회")
    @GetMapping(ApiPath.COMMENT_PORTFOLIO_ID)
    public FindCommentRs findComment(@PathVariable("portfolio-id") Long portfolioId) {
        return null;
    }

    @Operation(summary = "댓글 생성")
    @PostMapping(ApiPath.COMMENT)
    public void saveComment(@Validated @RequestBody SaveCommentRq rq) {
    }

    @Operation(summary = "댓글 수정")
    @GetMapping(ApiPath.COMMENT)
    public void updateComment(@Validated @RequestBody UpdateCommentRq rq) {
    }

    @Operation(summary = "댓글 삭제")
    @DeleteMapping(ApiPath.COMMENT)
    public void deleteComment(@Validated @RequestBody DeleteCommentRq rq) {
    }

}
