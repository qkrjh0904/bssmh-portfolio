package com.bssmh.portfolio.web.exception;

import org.springframework.http.HttpStatus;

public class ClassInfoException extends GeneralHttpException {
    public ClassInfoException() {
        super(HttpStatus.BAD_REQUEST, "학년 반 번호 정보가 잘못되었습니다.", null);
    }
}
