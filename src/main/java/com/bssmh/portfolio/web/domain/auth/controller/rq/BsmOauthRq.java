package com.bssmh.portfolio.web.domain.auth.controller.rq;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BsmOauthRq {

    @NotBlank
    @Schema(description = "BSM OAuth 인증 코드")
    private String authCode;

}
