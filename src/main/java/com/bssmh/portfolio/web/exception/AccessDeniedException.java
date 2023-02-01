package com.bssmh.portfolio.web.exception;

import org.springframework.http.HttpStatus;

public class AccessDeniedException extends GeneralHttpException {
    public AccessDeniedException() {
        super(HttpStatus.BAD_REQUEST, "권한이 없습니다.", null);
    }
}
