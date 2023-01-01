package com.bssmh.portfolio.web.config.security.exception;

import com.bssmh.portfolio.web.exception.GeneralHttpException;
import org.springframework.http.HttpStatus;

public class ExpiredJwtException extends GeneralHttpException {

	public ExpiredJwtException() {
		super(HttpStatus.BAD_REQUEST, "토큰시간이 만료되었습니다.", null);
	}
}
