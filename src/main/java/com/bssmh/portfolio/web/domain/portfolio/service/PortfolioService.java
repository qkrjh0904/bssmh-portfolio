package com.bssmh.portfolio.web.domain.portfolio.service;

import com.bssmh.portfolio.web.domain.endpoint.PagedResponse;
import com.bssmh.portfolio.web.domain.portfolio.controller.rq.DeletePortfolioRq;
import com.bssmh.portfolio.web.domain.portfolio.controller.rq.SavePortfolioRq;
import com.bssmh.portfolio.web.domain.portfolio.controller.rq.UpdatePortfolioRq;
import com.bssmh.portfolio.web.domain.portfolio.controller.rs.FindPortfolioRs;
import com.bssmh.portfolio.web.domain.portfolio.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;

    public void savePortfolio(SavePortfolioRq rq) {

    }

    public void deletePortfolio(DeletePortfolioRq rq) {

    }

    public void updatePortfolio(UpdatePortfolioRq rq) {

    }

    public FindPortfolioRs findPortfolio(Long portfolioId) {
        return null;
    }

    public PagedResponse<FindPortfolioRs> findPortfolioList() {
        return null;
    }
}
