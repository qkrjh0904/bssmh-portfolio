package com.bssmh.portfolio.db.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PortfolioTheme {
    WEB("웹"),
    APP("앱"),
    EMBEDDED("임베디드"),
    ROBOT("로봇");

    private final String name;

}
