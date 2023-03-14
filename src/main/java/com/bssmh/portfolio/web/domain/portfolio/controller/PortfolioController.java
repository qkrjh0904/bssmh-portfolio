package com.bssmh.portfolio.web.domain.portfolio.controller;

import com.bssmh.portfolio.web.config.security.context.MemberContext;
import com.bssmh.portfolio.web.domain.portfolio.controller.rq.AddPortfolioViewsCountRq;
import com.bssmh.portfolio.web.domain.portfolio.controller.rq.BookmarkPortfolioRq;
import com.bssmh.portfolio.web.domain.portfolio.controller.rq.DeletePortfolioRq;
import com.bssmh.portfolio.web.domain.portfolio.controller.rq.UpdatePortfolioRecommendStatusRq;
import com.bssmh.portfolio.web.domain.portfolio.controller.rq.UpdatePortfolioSequenceRq;
import com.bssmh.portfolio.web.domain.portfolio.controller.rq.UpsertPortfolioRq;
import com.bssmh.portfolio.web.domain.portfolio.service.PortfolioService;
import com.bssmh.portfolio.web.path.ApiPath;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "포트폴리오")
@RestController
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;


    @Operation(summary = "포트폴리오 생성")
    @PostMapping(ApiPath.PORTFOLIO)
    public void savePortfolio(@AuthenticationPrincipal MemberContext memberContext,
                              @Validated @RequestBody UpsertPortfolioRq rq) {
        portfolioService.savePortfolio(memberContext, rq);
    }

    @Operation(summary = "포트폴리오 삭제")
    @DeleteMapping(ApiPath.PORTFOLIO)
    public void deletePortfolio(@AuthenticationPrincipal MemberContext memberContext,
                                @Validated @RequestBody DeletePortfolioRq rq) {
        portfolioService.deletePortfolio(memberContext, rq);
    }

    @Operation(summary = "포트폴리오 수정")
    @PutMapping(ApiPath.PORTFOLIO)
    public void updatePortfolio(@AuthenticationPrincipal MemberContext memberContext,
                                @Validated @RequestBody UpsertPortfolioRq rq) {
        portfolioService.updatePortfolio(memberContext, rq);
    }

    @Operation(summary = "포트폴리오 좋아요", description = "toggle 방식")
    @PutMapping(ApiPath.PORTFOLIO_BOOKMARK)
    public void bookmarkPortfolio(@AuthenticationPrincipal MemberContext memberContext,
                                  @Validated @RequestBody BookmarkPortfolioRq rq) {
        portfolioService.bookmarkPortfolio(memberContext, rq);
    }

    @Operation(summary = "포트폴리오 순서 정렬")
    @PutMapping(ApiPath.PORTFOLIO_SEQUENCE)
    public void updatePortfolioSequence(@AuthenticationPrincipal MemberContext memberContext,
                                        @Validated @RequestBody UpdatePortfolioSequenceRq rq) {
        portfolioService.updatePortfolioSequence(memberContext, rq);
    }

    @Operation(summary = "포트폴리오 조회수 추가")
    @PutMapping(ApiPath.PORTFOLIO_VIEWS_ADD)
    public void addPortfolioViewsCount(@Validated @RequestBody AddPortfolioViewsCountRq rq) {
        portfolioService.addPortfolioViewsCount(rq);
    }

    @Operation(summary = "포트폴리오 추천 (toggle 방식)")
    @PutMapping(ApiPath.PORTFOLIO_RECOMMEND)
    public void updatePortfolioRecommendStatus(@AuthenticationPrincipal MemberContext memberContext,
                                               @Validated @RequestBody UpdatePortfolioRecommendStatusRq rq) {
        portfolioService.updatePortfolioRecommendStatus(memberContext, rq);
    }
}
