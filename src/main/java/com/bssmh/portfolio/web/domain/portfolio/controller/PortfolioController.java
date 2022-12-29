package com.bssmh.portfolio.web.domain.portfolio.controller;

import com.bssmh.portfolio.web.domain.portfolio.controller.rq.BookmarkPortfolioRq;
import com.bssmh.portfolio.web.domain.portfolio.controller.rq.DeletePortfolioRq;
import com.bssmh.portfolio.web.domain.portfolio.controller.rq.SavePortfolioRq;
import com.bssmh.portfolio.web.domain.portfolio.controller.rq.UpdatePortfolioRq;
import com.bssmh.portfolio.web.domain.portfolio.service.PortfolioService;
import com.bssmh.portfolio.web.path.ApiPath;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
    public void savePortfolio(@Validated @RequestBody SavePortfolioRq rq) {
        portfolioService.savePortfolio(rq);
    }

    @Operation(summary = "포트폴리오 삭제")
    @DeleteMapping(ApiPath.PORTFOLIO)
    public void deletePortfolio(@Validated @RequestBody DeletePortfolioRq rq) {
        portfolioService.deletePortfolio(rq);
    }

    @Operation(summary = "포트폴리오 수정")
    @PutMapping(ApiPath.PORTFOLIO)
    public void updatePortfolio(@Validated @RequestBody UpdatePortfolioRq rq) {
        portfolioService.updatePortfolio(rq);
    }

    @Operation(summary = "포트폴리오 좋아요",
            description = "toggle 방식")
    @PutMapping(ApiPath.PORTFOLIO_BOOKMARK)
    public void bookmarkPortfolio(@Validated @RequestBody BookmarkPortfolioRq rq) {
    }

}