package com.bssmh.portfolio.web.domain.portfolio.repository;

import com.bssmh.portfolio.db.entity.portfolio.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long>, PortfolioRepositoryCustom {
}
