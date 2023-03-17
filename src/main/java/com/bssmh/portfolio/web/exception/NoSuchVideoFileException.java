package com.bssmh.portfolio.web.exception;

import org.springframework.http.HttpStatus;

public class NoSuchVideoFileException extends GeneralHttpException {
    public NoSuchVideoFileException() {
        super(HttpStatus.BAD_REQUEST, "비디오 파일을 찾을 수 없습니다.", null);
    }
}
