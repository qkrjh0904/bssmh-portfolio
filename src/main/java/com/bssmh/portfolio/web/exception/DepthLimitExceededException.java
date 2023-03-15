package com.bssmh.portfolio.web.exception;

import org.springframework.http.HttpStatus;

public class DepthLimitExceededException extends GeneralHttpException {
    public DepthLimitExceededException() {
        super(HttpStatus.BAD_REQUEST, "대댓글 개수가 제한을 초과했습니다", null);
    }

}
