package com.bssmh.portfolio.web.domain.portfolio.controller.rq;

import com.bssmh.portfolio.db.enums.PortfolioSortType;
import com.bssmh.portfolio.db.enums.SortDirectionType;
import com.bssmh.portfolio.db.enums.UploadDateType;
import com.bssmh.portfolio.web.domain.portfolio.repository.PortfolioRepositoryImpl;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class SearchPortfolioFilterRq {

    @Schema(description = "검색어")
    private String search;

    @Schema(description = "업로드 날짜 필터 타입")
    private UploadDateType uploadDateType;

    @Schema(description = "학년 필터")
    private Integer schoolGrade;

    @Schema(description = "정렬기준")
    private PortfolioSortType sortType;

    @Schema(description = "정렬방향")
    private SortDirectionType sortDirectionType;

}
