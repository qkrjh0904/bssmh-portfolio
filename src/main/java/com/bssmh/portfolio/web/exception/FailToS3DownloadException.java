package com.bssmh.portfolio.web.exception;

import org.springframework.http.HttpStatus;

public class FailToS3DownloadException extends GeneralHttpException {

	public FailToS3DownloadException() {
		super(HttpStatus.INTERNAL_SERVER_ERROR, "S3 다운로드에 실패했습니다.", null);
	}
}
