package com.bssmh.portfolio.db.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PortfolioType {
    VIDEO("영상"),
    URL("홈페이지 URL"),
    ALL("영상, URL 모두");

    private final String name;

}
