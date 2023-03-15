package com.bssmh.portfolio.web.domain.portfolio.service;

import com.bssmh.portfolio.db.entity.bookmark.Bookmark;
import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.db.entity.portfolio.Portfolio;
import com.bssmh.portfolio.web.domain.portfolio.repository.PortfolioBookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class PortfolioBookmarkService {

    // service
    private final FindPortfolioBookmarkService findPortfolioBookmarkService;

    // repository
    private final PortfolioBookmarkRepository portfolioBookmarkRepository;

    public void toggleBookmarkPortfolio(Member member, Portfolio portfolio) {

        Bookmark bookmark = findPortfolioBookmarkService.findByMemberAndPortfolioOrElseNull(member, portfolio);
        if (Objects.nonNull(bookmark)) {
            portfolioBookmarkRepository.delete(bookmark);
            return;
        }

        Bookmark newBookmark = Bookmark.create(portfolio, member);
        portfolioBookmarkRepository.save(newBookmark);
    }
}
