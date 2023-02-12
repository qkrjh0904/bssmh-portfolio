package com.bssmh.portfolio.web.domain.portfolio.service;

import com.bssmh.portfolio.db.entity.bookmark.Bookmark;
import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.db.entity.portfolio.Portfolio;
import com.bssmh.portfolio.web.domain.portfolio.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkService {

    // service
    private final FindBookmarkService findBookmarkService;

    // repository
    private final BookmarkRepository bookmarkRepository;
    public void toggleBookmarkPortfolio(Member member, Portfolio portfolio) {
        Bookmark bookmark = findBookmarkService.findByMemberAndPortfolioOrElseNull(member, portfolio);
        if (bookmark != null) {
            bookmarkRepository.delete(bookmark);
            return;
        }
        Bookmark newBookmark = Bookmark.create(
                portfolio,
                member);
        bookmarkRepository.save(newBookmark);
        portfolio.addBookmarkList(newBookmark);
    }
}
