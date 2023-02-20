package com.bssmh.portfolio.web.domain.portfolio.controller.rq;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class PaginationRq {

    @Schema(description = "페이지")
    private Integer page;

    @Schema(description = "페이지 크기")
    private Integer size;

}
