package com.bssmh.portfolio.web.exception;

import org.springframework.http.HttpStatus;

public class PortfolioUrlEmptyException extends GeneralHttpException {
    public PortfolioUrlEmptyException() {
        super(HttpStatus.BAD_REQUEST, "포트폴리오 url 은 빈값일 수 없습니다.", null);
    }
}
