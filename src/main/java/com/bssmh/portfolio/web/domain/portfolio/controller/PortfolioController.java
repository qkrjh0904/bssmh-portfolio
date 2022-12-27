package com.bssmh.portfolio.web.domain.portfolio.controller;

import com.bssmh.portfolio.web.domain.endpoint.PagedResponse;
import com.bssmh.portfolio.web.domain.path.ApiPath;
import com.bssmh.portfolio.web.domain.portfolio.controller.rq.DeletePortfolioRq;
import com.bssmh.portfolio.web.domain.portfolio.controller.rq.SavePortfolioRq;
import com.bssmh.portfolio.web.domain.portfolio.controller.rq.UpdatePortfolioRq;
import com.bssmh.portfolio.web.domain.portfolio.controller.rs.FindPortfolioRs;
import com.bssmh.portfolio.web.domain.portfolio.service.PortfolioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Operation(summary = "포트폴리오 상세 조회")
    @GetMapping(ApiPath.PORTFOLIO)
    public FindPortfolioRs findPortfolio() {
        return portfolioService.findPortfolio();
    }

    @Operation(summary = "포트폴리오 목록 조회")
    @GetMapping(ApiPath.PORTFOLIO)
    public PagedResponse<FindPortfolioRs> findPortfolioList() {
        return portfolioService.findPortfolioList();
    }

}
