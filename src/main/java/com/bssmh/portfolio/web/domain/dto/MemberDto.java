package com.bssmh.portfolio.web.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberDto {

    @Schema(description = "멤버 id")
    private Long memberId;
    
    @Schema(description = "이름")
    private String name;

    @Schema(description = "프로필사진 url")
    private String profileImageUrl;

    @Schema(description = "이메일")
    private String email;

}
