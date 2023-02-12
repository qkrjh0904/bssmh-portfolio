package com.bssmh.portfolio.db.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum PortfolioScope {
    PUBLIC("전체 공개"),
    PROTECTED("일부 공개"),
    PRIVATE("비공개");

    private final String name;


    public static Boolean getMoreThanProtected(PortfolioScope portfolioScope) {
        return List.of(PUBLIC, PROTECTED).contains(portfolioScope);
    }
}
