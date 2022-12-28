package com.bssmh.portfolio.db.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PortfolioType {
    VIDEO("영상"),
    URL("홈페이지 URL");

    private final String name;

}
