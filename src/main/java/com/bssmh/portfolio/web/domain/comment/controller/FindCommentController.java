package com.bssmh.portfolio.web.domain.comment.controller;

import com.bssmh.portfolio.web.config.security.context.MemberContext;
import com.bssmh.portfolio.web.domain.comment.controller.rs.FindCommentRs;
import com.bssmh.portfolio.web.domain.comment.service.FindCommentService;
import com.bssmh.portfolio.web.endpoint.ListResponse;
import com.bssmh.portfolio.web.path.ApiPath;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "댓글 조회")
@RestController
@RequiredArgsConstructor
public class FindCommentController {

    private final FindCommentService findCommentService;

    @Operation(summary = "댓글 리스트 조회")
    @GetMapping(ApiPath.COMMENT_PORTFOLIO_ID)
    public ListResponse<FindCommentRs> findComment(@AuthenticationPrincipal MemberContext memberContext,
                                                   @PathVariable("portfolio-id") Long portfolioId) {
        return findCommentService.findCommentByPortfolioId(memberContext, portfolioId);
    }

}
