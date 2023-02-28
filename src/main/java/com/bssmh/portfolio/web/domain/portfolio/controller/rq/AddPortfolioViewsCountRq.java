package com.bssmh.portfolio.web.domain.portfolio.controller.rq;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class AddPortfolioViewsCountRq {

    @NotNull
    @Schema(description = "포트폴리오 id")
    private Long portfolioId;

}
