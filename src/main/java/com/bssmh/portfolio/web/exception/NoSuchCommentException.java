package com.bssmh.portfolio.web.exception;

import org.springframework.http.HttpStatus;

public class NoSuchCommentException extends GeneralHttpException {
    public NoSuchCommentException() {
        super(HttpStatus.BAD_REQUEST, "댓글을 찾을 수 없습니다.", null);
    }
}
