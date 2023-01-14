package com.bssmh.portfolio.web.domain.auth.controller;

import com.bssmh.portfolio.web.domain.auth.service.AuthService;
import com.bssmh.portfolio.web.domain.auth.service.BsmOauthService;
import com.bssmh.portfolio.web.domain.dto.JwtTokenDto;
import com.bssmh.portfolio.web.path.ApiPath;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Tag(name = "인증")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final BsmOauthService bsmOauthService;

    @Operation(summary = "BSM OAuth 로그인")
    @PostMapping(ApiPath.BSM_OAUTH)
    public JwtTokenDto bsmLogin(@RequestParam("authCode") String authCode) throws IOException {
        return authService.loginPostProcess(
                bsmOauthService.bsmLogin(authCode)
        );
    }

}
