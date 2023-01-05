package com.bssmh.portfolio.web.exception;

import org.springframework.http.HttpStatus;

public class NoSuchAttachFileException extends GeneralHttpException {
    public NoSuchAttachFileException() {
        super(HttpStatus.BAD_REQUEST, "첨부파일을 찾을 수 없습니다.", null);
    }
}
