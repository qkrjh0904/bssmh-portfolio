package com.bssmh.portfolio.web.domain.member.controller.rq;

import com.bssmh.portfolio.db.enums.MemberType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SignupMemberRq {

    @Schema(description = "학생/선생님")
    private MemberType memberType;

    @Schema(description = "전화번호")
    private String phone;

    @Schema(description = "소속")
    private String belong;

    @Schema(description = "학년")
    private Integer schoolGrade;

    @Schema(description = "반")
    private Integer schoolClass;

    @Schema(description = "번호")
    private Integer schoolNumber;

    @Schema(description = "입학년도 yyyy-MM-dd")
    private String admissionDate;

}
