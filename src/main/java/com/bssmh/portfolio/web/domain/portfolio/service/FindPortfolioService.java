package com.bssmh.portfolio.web.domain.portfolio.service;

import com.bssmh.portfolio.web.domain.portfolio.controller.rs.FindPortfolioDetailRs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FindPortfolioService {

    public FindPortfolioDetailRs findPortfolio(Long portfolioId) {
        return null;
    }

}
