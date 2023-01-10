package com.bssmh.portfolio.web.domain.auth.controller;

import com.bssmh.portfolio.web.path.ApiPath;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import leehj050211.bsmOauth.BsmOauth;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인증")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final BsmOauth bsmOauth;

    @Operation(summary = "BSM OAuth 로그인")
    @GetMapping(ApiPath.BSM_OAUTH)
    public void login(@PathVariable("authCode") String authCode) {

    }

}
