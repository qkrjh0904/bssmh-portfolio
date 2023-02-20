package com.bssmh.portfolio.web.domain.portfolio.controller.rq;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class SearchPortfolioListRq {

    @Schema(description = "페이지네이션")
    private PaginationRq pagination;

    @Schema(description = "필터 항목")
    private SearchPortfolioFilterRq filter;
}
