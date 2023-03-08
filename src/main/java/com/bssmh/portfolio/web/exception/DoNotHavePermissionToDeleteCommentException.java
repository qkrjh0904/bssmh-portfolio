package com.bssmh.portfolio.web.exception;

import org.springframework.http.HttpStatus;

public class DoNotHavePermissionToDeleteCommentException extends GeneralHttpException {
    public DoNotHavePermissionToDeleteCommentException() {
        super(HttpStatus.FORBIDDEN, "댓글 삭제 권한이 없습니다.", null);
    }
}
