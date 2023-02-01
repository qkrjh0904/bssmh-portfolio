package com.bssmh.portfolio.web.exception;

import org.springframework.http.HttpStatus;

public class SelfFollowNotAllowedException extends GeneralHttpException {
    public SelfFollowNotAllowedException() {
        super(HttpStatus.BAD_REQUEST, "자기 자신을 팔로우할 수 없습니다.", null);
    }
}
