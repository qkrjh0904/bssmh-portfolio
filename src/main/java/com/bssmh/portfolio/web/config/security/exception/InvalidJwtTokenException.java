package com.bssmh.portfolio.web.config.security.exception;

import com.bssmh.portfolio.web.exception.GeneralHttpException;
import org.springframework.http.HttpStatus;

public class InvalidJwtTokenException extends GeneralHttpException {
    public InvalidJwtTokenException() {
        super(HttpStatus.BAD_REQUEST, "유효한 토큰이 아닙니다.", null);
    }
}
