package com.bssmh.portfolio.web.domain.member.controller.rq;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UpdateMemberRq {

    @Schema(description = "별명")
    private String nickName;

    @Schema(description = "자기소개 등")
    private String description;

    @Schema(description = "전화번호")
    private String phone;

    @Schema(description = "직군, 직무, 전공 등")
    private String job;

    @Schema(description = "소속")
    private String belong;

    @Schema(description = "학년")
    private Integer schoolGrade;

    @Schema(description = "반")
    private Integer schoolClass;

    @Schema(description = "번호")
    private Integer schoolNumber;

    @Schema(description = "입학년도 yyyy")
    private Integer admissionYear;

}
