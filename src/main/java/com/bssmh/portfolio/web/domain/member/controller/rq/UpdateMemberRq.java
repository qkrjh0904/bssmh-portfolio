package com.bssmh.portfolio.web.domain.member.controller.rq;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateMemberRq {

    @Schema(description = "별명")
    private String nickName;

    @Schema(description = "자기소개 등")
    private String description;

    @Schema(description = "전화번호")
    private String phone;

    @Schema(description = "직군, 직무, 전공 등")
    private String job;

}
