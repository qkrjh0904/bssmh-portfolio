package com.bssmh.portfolio.web.domain.portfolio.service;

import com.bssmh.portfolio.db.entity.bookmark.Bookmark;
import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.db.entity.portfolio.Portfolio;
import com.bssmh.portfolio.db.entity.portfolio.PortfolioSkill;
import com.bssmh.portfolio.web.domain.portfolio.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    public void addBookmarkList(Member member, Portfolio portfolio) {
        Bookmark bookmark = Bookmark.create(
                portfolio,
                member);
        bookmarkRepository.save(bookmark);

        portfolio.addBookmarkList(bookmark);
    }
}
