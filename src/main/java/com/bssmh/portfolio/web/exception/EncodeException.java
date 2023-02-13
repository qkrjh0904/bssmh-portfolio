package com.bssmh.portfolio.web.exception;

import org.springframework.http.HttpStatus;

public class EncodeException extends GeneralHttpException {

	public EncodeException() {
		super(HttpStatus.INTERNAL_SERVER_ERROR, "인코딩에 실패했습니다.", null);
	}
}
