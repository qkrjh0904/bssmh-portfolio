package com.bssmh.portfolio.web.exception;

import org.springframework.http.HttpStatus;

public class NoSuchFollowException extends GeneralHttpException {
    public NoSuchFollowException() {
        super(HttpStatus.BAD_REQUEST, "팔로우 대상을 찾을 수 없습니다.", null);
    }
}
