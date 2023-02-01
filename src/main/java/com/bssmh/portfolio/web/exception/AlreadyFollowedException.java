package com.bssmh.portfolio.web.exception;

import org.springframework.http.HttpStatus;

public class AlreadyFollowedException extends GeneralHttpException {
    public AlreadyFollowedException() {
        super(HttpStatus.BAD_REQUEST, "이미 팔로우했습니다.", null);
    }
}
