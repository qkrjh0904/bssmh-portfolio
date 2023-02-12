package com.bssmh.portfolio.web.domain.portfolio.repository;

import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.db.entity.portfolio.Portfolio;
import com.bssmh.portfolio.db.enums.PortfolioScope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PortfolioRepositoryCustom {
    Page<Portfolio> findPortfolioListBySearch(String search, Pageable pageable);

    Page<Portfolio> findMyPortfolio(Long memberId, Pageable pageable);

    Page<Portfolio> findMemberPortfolio(Long memberId, Pageable pageable);
}
