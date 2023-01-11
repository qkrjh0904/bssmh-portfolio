package com.bssmh.portfolio.web.exception;

import org.springframework.http.HttpStatus;

public class NoSuchPortfolioException extends GeneralHttpException {
    public NoSuchPortfolioException() {
        super(HttpStatus.BAD_REQUEST, "포트폴리오를 찾을 수 없습니다.", null);
    }
}
