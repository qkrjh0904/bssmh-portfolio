package com.bssmh.portfolio.web.endpoint;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ErrorResponse {
    private Integer statusCode;
    private String reason;
    private String message;
}
