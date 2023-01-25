package com.bssmh.portfolio.web.exception;

import org.springframework.http.HttpStatus;

public class InvalidBsmOauthClientException extends GeneralHttpException {
    public InvalidBsmOauthClientException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "BSM OAuth 클라이언트 인증에 실패하였습니다.", null);
    }
}
