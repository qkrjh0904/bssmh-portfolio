package com.bssmh.portfolio.web.exception;

import org.springframework.http.HttpStatus;

public class NotMatchedExtensionException extends GeneralHttpException {
    public NotMatchedExtensionException(String extension) {
        super(HttpStatus.BAD_REQUEST, "해당 확장자를 가진 파일을 업로드 할 수 없습니다. extension: " + extension, null);
    }
}
