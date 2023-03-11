package com.bssmh.portfolio.web.domain.portfolio.repository;

import com.bssmh.portfolio.db.entity.bookmark.PortfolioBookmark;
import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.db.entity.portfolio.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PortfolioBookmarkRepository extends JpaRepository<PortfolioBookmark, Long> {
    Optional<PortfolioBookmark> findByMemberAndPortfolio(Member member, Portfolio portfolio);
    List<PortfolioBookmark> findByMember(Member member);

}
