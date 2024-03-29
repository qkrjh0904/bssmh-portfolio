package com.bssmh.portfolio.web.domain.portfolio.controller.rq;

import com.bssmh.portfolio.db.enums.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class SearchPortfolioFilterRq {

    @Schema(description = "검색어")
    private String search;

    @Schema(description = "검색 종류")
    private SearchType searchType;

    @Schema(description = "업로드 날짜 필터 타입")
    private UploadDateType uploadDateType;

    @Schema(description = "학년 필터")
    private Integer schoolGrade;

    @Schema(description = "포트폴리오 테마")
    private PortfolioTheme portfolioTheme;

    @Schema(description = "정렬기준")
    private PortfolioSortType sortType;

    @Schema(description = "정렬방향")
    private SortDirectionType sortDirectionType;

    @Schema(description = "추천 상태")
    private PortfolioRecommendStatus recommendStatus;

}
