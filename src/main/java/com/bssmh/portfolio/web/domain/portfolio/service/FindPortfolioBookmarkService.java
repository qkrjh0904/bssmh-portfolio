package com.bssmh.portfolio.web.domain.portfolio.service;
import com.bssmh.portfolio.db.entity.bookmark.Bookmark;
import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.db.entity.portfolio.Portfolio;
import com.bssmh.portfolio.web.domain.portfolio.repository.PortfolioBookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FindPortfolioBookmarkService {
    private final PortfolioBookmarkRepository portfolioBookmarkRepository;

    public Bookmark findByMemberAndPortfolioOrElseNull(Member member, Portfolio portfolio) {
        return portfolioBookmarkRepository.findByMemberAndPortfolio(member, portfolio)
                .orElse(null);
    }

    public List<Bookmark> findByMember(Member member) {
        return portfolioBookmarkRepository.findByMember(member);
    }
}
