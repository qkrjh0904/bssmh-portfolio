package com.bssmh.portfolio.db.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PortfolioRecommendStatus {

    NONE("일반 포트폴리오"),
    RECOMMEND("추천 포트폴리오");

    private final String name;

    public static PortfolioRecommendStatus getOppositeStatus(PortfolioRecommendStatus status) {
        if (NONE.equals(status)) {
            return RECOMMEND;
        }

        return NONE;
    }
}
