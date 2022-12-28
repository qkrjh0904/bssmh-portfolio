package com.bssmh.portfolio.web.domain.portfolio.controller;

import com.bssmh.portfolio.web.domain.portfolio.controller.rs.FindPortfolioDetailRs;
import com.bssmh.portfolio.web.domain.portfolio.controller.rs.FindPortfolioListRs;
import com.bssmh.portfolio.web.domain.portfolio.service.FindPortfolioService;
import com.bssmh.portfolio.web.endpoint.PagedResponse;
import com.bssmh.portfolio.web.endpoint.Pagination;
import com.bssmh.portfolio.web.path.ApiPath;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "포트폴리오")
@RestController
@RequiredArgsConstructor
public class FindPortfolioController {

    private final FindPortfolioService findPortfolioService;

    @Operation(summary = "포트폴리오 상세 조회")
    @GetMapping(ApiPath.PORTFOLIO_ID)
    public FindPortfolioDetailRs findPortfolio(@PathVariable("portfolio-id") Long portfolioId) {
        return findPortfolioService.findPortfolio(portfolioId);
    }

    @Operation(summary = "포트폴리오 검색",
            description = "검색어가 없을 때 전체조회")
    @GetMapping(ApiPath.PORTFOLIO_SEARCH)
    public PagedResponse<FindPortfolioListRs> searchPortfolioList(@RequestParam(value = "search") String search,
                                                                  Pagination pagination) {
        return null;
    }

    @Operation(summary = "내 포트폴리오 리스트 조회",
            description = "PUBLIC/PRIVATE/PROTECTED 포트폴리오 모두 조회")
    @GetMapping(ApiPath.PORTFOLIO_MY)
    public PagedResponse<FindPortfolioListRs> findMyPortfolio(Pagination pagination) {
        return null;
    }

    @Operation(summary = "다른 멤버 포트폴리오 리스트 조회",
            description = "PUBLIC 포트폴리오만 조회 가능")
    @GetMapping(ApiPath.PORTFOLIO_MEMBER_ID)
    public PagedResponse<FindPortfolioListRs> findMemberPortfolio(@PathVariable("member-id") Long memberId,
                                                                  Pagination pagination) {
        return null;
    }

}
