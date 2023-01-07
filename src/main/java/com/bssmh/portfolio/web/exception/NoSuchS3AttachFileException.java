package com.bssmh.portfolio.web.exception;

import org.springframework.http.HttpStatus;

public class NoSuchS3AttachFileException extends GeneralHttpException {
    public NoSuchS3AttachFileException(String fileUid) {
        super(HttpStatus.BAD_REQUEST, "S3 에서 파일을 찾을 수 없습니다. fileUid : " + fileUid, null);
    }
}
