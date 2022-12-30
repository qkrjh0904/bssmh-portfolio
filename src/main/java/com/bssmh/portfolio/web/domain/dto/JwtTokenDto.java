package com.bssmh.portfolio.web.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtTokenDto {

    @Schema(description = "jwt token")
    private String token;

    @Schema(description = "토큰 만료 시간")
    private String validity;

}
