package com.bssmh.portfolio.web.domain.auth.controller;

import com.bssmh.portfolio.web.config.security.context.MemberContext;
import com.bssmh.portfolio.web.domain.auth.controller.rq.BsmOauthRq;
import com.bssmh.portfolio.web.domain.auth.controller.rq.OAuth2Rq;
import com.bssmh.portfolio.web.domain.auth.service.AuthService;
import com.bssmh.portfolio.web.domain.auth.service.BsmOauthService;
import com.bssmh.portfolio.web.domain.auth.service.OAuth2Service;
import com.bssmh.portfolio.web.domain.dto.JwtTokenDto;
import com.bssmh.portfolio.web.path.ApiPath;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인증")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final BsmOauthService bsmOauthService;
    private final OAuth2Service oAuth2Service;
    private final AuthService authService;

    @Deprecated
    @Operation(summary = "BSM OAuth 로그인")
    @PostMapping(ApiPath.BSM_OAUTH)
    public JwtTokenDto bsmLogin(@Validated @RequestBody BsmOauthRq rq) {
        return bsmOauthService.bsmLogin(rq.getAuthCode());
    }

    @Operation(summary = "OAuth 로그인")
    @PostMapping(ApiPath.LOGIN_OAUTH2)
    public JwtTokenDto loginOAuth2(@Validated @RequestBody OAuth2Rq rq) {
        return oAuth2Service.loginOAuth2(rq);
    }

    @Operation(summary = "토큰 재발급")
    @PostMapping(ApiPath.REFRESH_TOKEN)
    public JwtTokenDto refreshToken(@AuthenticationPrincipal MemberContext memberContext) {
        return authService.refreshToken(memberContext);
    }

}
