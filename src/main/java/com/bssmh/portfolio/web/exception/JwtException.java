package com.bssmh.portfolio.web.exception;

import org.springframework.http.HttpStatus;

public class JwtException extends GeneralHttpException {

	public JwtException(String message) {
		super(HttpStatus.UNAUTHORIZED, message, null);
	}
}
