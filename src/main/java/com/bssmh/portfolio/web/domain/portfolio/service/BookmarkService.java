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

    private final BookmarkRepository bookmarkRepository;
    public void toggleBookmarkList(Member member, Portfolio portfolio) {
        Long BookmarkCount = bookmarkRepository.countByMember(member);
        // 좋아요가 없는 상태라면
        if (BookmarkCount == 0) {
            Bookmark bookmark = Bookmark.create(
                    portfolio,
                    member);
            bookmarkRepository.save(bookmark);
            portfolio.addBookmarkList(bookmark);
            return;
        }
        bookmarkRepository.deleteBookmarkByMember(member);
    }
}
