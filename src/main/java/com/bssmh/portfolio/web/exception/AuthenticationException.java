package com.bssmh.portfolio.web.exception;

import org.springframework.http.HttpStatus;

public class AuthenticationException extends GeneralHttpException {
    public AuthenticationException() {
        super(HttpStatus.UNAUTHORIZED, "인증에 실패했습니다.", null);
    }
}
