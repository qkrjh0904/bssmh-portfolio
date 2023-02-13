package com.bssmh.portfolio.web.exception;

import com.bssmh.portfolio.web.endpoint.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(GeneralHttpException.class)
    public ResponseEntity<ErrorResponse> errorHandler(GeneralHttpException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(e.getHttpStatus().value())
                .reason(e.getHttpStatus().getReasonPhrase())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, e.getHttpStatus());
    }

}
