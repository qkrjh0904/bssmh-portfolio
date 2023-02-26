package com.bssmh.portfolio.web.domain.portfolio.repository;

import com.bssmh.portfolio.db.entity.portfolio.Portfolio;
import com.bssmh.portfolio.web.domain.portfolio.controller.rq.SearchPortfolioFilterRq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PortfolioRepositoryCustom {
    Page<Portfolio> findPortfolioListBySearch(SearchPortfolioFilterRq filter, Pageable pageable);

    Page<Portfolio> findMyPortfolio(Long memberId, Pageable pageable);

    Page<Portfolio> findMemberPortfolio(Long memberId, Pageable pageable);

    Optional<Portfolio> findMyLastSequencePortfolio(Long memberId);
}
