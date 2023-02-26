package com.bssmh.portfolio.web.exception;

import org.springframework.http.HttpStatus;

public class PortfolioEmptyException extends GeneralHttpException {
    public PortfolioEmptyException(String message) {
        super(HttpStatus.BAD_REQUEST, "포트폴리오 필수값이 비었습니다. 필수값 : " + message, null);
    }
}
