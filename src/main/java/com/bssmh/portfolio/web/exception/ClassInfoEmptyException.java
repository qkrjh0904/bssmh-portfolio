package com.bssmh.portfolio.web.exception;

import org.springframework.http.HttpStatus;

public class ClassInfoEmptyException extends GeneralHttpException {
    public ClassInfoEmptyException() {
        super(HttpStatus.BAD_REQUEST, "학년 반 번호 정보가 비어있습니다.", null);
    }
}
