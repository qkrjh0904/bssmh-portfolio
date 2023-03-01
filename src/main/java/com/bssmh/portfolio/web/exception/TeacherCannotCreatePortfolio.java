package com.bssmh.portfolio.web.exception;

import org.springframework.http.HttpStatus;

public class TeacherCannotCreatePortfolio extends GeneralHttpException {
    public TeacherCannotCreatePortfolio() {
        super(HttpStatus.BAD_REQUEST, "선생님은 포트폴리오를 생성할 수 없습니다.", null);
    }
}
