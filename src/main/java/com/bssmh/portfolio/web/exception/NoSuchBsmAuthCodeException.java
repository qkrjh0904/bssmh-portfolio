package com.bssmh.portfolio.web.exception;

import org.springframework.http.HttpStatus;

public class NoSuchBsmAuthCodeException extends GeneralHttpException {
    public NoSuchBsmAuthCodeException() {
        super(HttpStatus.NOT_FOUND, "BSM OAuth 인증코드를 찾을 수 없습니다.", null);
    }
}
