package com.bssmh.portfolio.web.exception;

import org.springframework.http.HttpStatus;

public class AwsS3FileUploadException extends GeneralHttpException {

	public AwsS3FileUploadException() {
		super(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.", null);
	}
}
