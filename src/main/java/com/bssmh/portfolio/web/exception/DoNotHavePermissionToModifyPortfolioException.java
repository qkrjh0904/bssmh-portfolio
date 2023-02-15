package com.bssmh.portfolio.web.exception;

import org.springframework.http.HttpStatus;

public class DoNotHavePermissionToModifyPortfolioException extends GeneralHttpException {
    public DoNotHavePermissionToModifyPortfolioException() {
        super(HttpStatus.FORBIDDEN, "포트폴리오 수정 권한이 없습니다.", null);
    }
}
