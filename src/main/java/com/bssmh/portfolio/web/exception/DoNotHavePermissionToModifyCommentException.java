package com.bssmh.portfolio.web.exception;

import org.springframework.http.HttpStatus;

public class DoNotHavePermissionToModifyCommentException extends GeneralHttpException {
    public DoNotHavePermissionToModifyCommentException() {
        super(HttpStatus.BAD_REQUEST, "댓글 수정 권한이 없습니다.", null);
    }
}
