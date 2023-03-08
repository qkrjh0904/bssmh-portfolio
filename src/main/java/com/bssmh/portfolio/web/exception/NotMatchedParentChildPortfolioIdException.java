package com.bssmh.portfolio.web.exception;

import org.springframework.http.HttpStatus;

public class NotMatchedParentChildPortfolioIdException extends GeneralHttpException {
    public NotMatchedParentChildPortfolioIdException() {
        super(HttpStatus.BAD_REQUEST, "부모댓글과 자식댓글의 포트폴리오 Id가 일치하지 않습니다.", null);
    }

}
