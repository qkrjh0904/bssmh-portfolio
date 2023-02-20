package com.bssmh.portfolio.db.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PortfolioSortType {

    UPLOAD_DATE("업로드 날짜"),
    VIEWS("조회수"),
    BOOKMARKS("좋아요 수"),
    COMMENTS("댓글 수"),
    RANK("랭킹순");


    private final String name;

}
