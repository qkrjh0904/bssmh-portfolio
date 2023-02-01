package com.bssmh.portfolio.web.domain.auth.controller.rq;

import com.bssmh.portfolio.web.domain.enums.ClientType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
public class OAuth2Rq {

    @NotNull
    @Schema(description = "클라이언트 타입")
    private ClientType clientType;

    @NotEmpty
    @Schema(description = "OAuth2 인증 코드")
    private String code;

}
