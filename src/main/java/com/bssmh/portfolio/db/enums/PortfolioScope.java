package com.bssmh.portfolio.db.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PortfolioScope {
    PUBLIC("전체 공개"),
    PROTECTED("일부 공개"),
    PRIVATE("비공개");

    private final String name;

}
