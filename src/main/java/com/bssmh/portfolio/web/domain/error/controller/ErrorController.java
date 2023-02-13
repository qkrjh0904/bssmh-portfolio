package com.bssmh.portfolio.web.domain.error.controller;

import com.bssmh.portfolio.web.exception.JwtException;
import com.bssmh.portfolio.web.path.ApiPath;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
public class ErrorController {

    @GetMapping(ApiPath.ERROR_AUTH)
    public void errorAuth(@RequestParam(value = "message") String message) {
        throw new JwtException(message);
    }
}
