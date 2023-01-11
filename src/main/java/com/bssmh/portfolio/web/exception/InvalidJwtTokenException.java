package com.bssmh.portfolio.web.exception;

import org.springframework.http.HttpStatus;

public class InvalidJwtTokenException extends GeneralHttpException {
    public InvalidJwtTokenException() {
        super(HttpStatus.BAD_REQUEST, "유효한 토큰이 아닙니다.", null);
    }
}
