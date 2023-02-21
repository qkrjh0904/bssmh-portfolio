package com.bssmh.portfolio.web.domain.portfolio.controller.rq;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class UpdatePortfolioSequenceRq {
    @Schema(description = "포트폴리오 ID 리스트")
    private List<Long> portfolioIdList;
}
