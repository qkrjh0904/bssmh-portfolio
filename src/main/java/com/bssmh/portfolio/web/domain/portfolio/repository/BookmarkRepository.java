package com.bssmh.portfolio.web.domain.portfolio.repository;

import com.bssmh.portfolio.db.entity.bookmark.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
}
