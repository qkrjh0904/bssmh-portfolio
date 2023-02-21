package com.bssmh.portfolio.web.exception;

import org.springframework.http.HttpStatus;

public class PortfolioSequenceException extends GeneralHttpException {
    public PortfolioSequenceException() {
        super(HttpStatus.BAD_REQUEST, "포트폴리오 순서를 변경할 수 없습니다.", null);
    }
}
