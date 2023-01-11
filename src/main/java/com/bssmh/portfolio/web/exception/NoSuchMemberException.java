package com.bssmh.portfolio.web.exception;

import org.springframework.http.HttpStatus;

public class NoSuchMemberException extends GeneralHttpException {
    public NoSuchMemberException() {
        super(HttpStatus.BAD_REQUEST, "멤버를 찾을 수 없습니다.", null);
    }
}
