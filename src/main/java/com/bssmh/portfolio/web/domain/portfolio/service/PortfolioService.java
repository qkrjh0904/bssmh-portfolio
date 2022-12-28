package com.bssmh.portfolio.web.domain.portfolio.service;

import com.bssmh.portfolio.web.domain.portfolio.controller.rq.DeletePortfolioRq;
import com.bssmh.portfolio.web.domain.portfolio.controller.rq.SavePortfolioRq;
import com.bssmh.portfolio.web.domain.portfolio.controller.rq.UpdatePortfolioRq;
import com.bssmh.portfolio.web.domain.portfolio.controller.rs.FindPortfolioDetailRs;
import com.bssmh.portfolio.web.domain.portfolio.controller.rs.FindPortfolioListRs;
import com.bssmh.portfolio.web.domain.portfolio.repository.PortfolioRepository;
import com.bssmh.portfolio.web.endpoint.PagedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;

    public void savePortfolio(SavePortfolioRq rq) {

    }

    public void deletePortfolio(DeletePortfolioRq rq) {

    }

    public void updatePortfolio(UpdatePortfolioRq rq) {

    }
}
