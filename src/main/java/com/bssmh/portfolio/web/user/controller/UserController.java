package com.bssmh.portfolio.web.user.controller;

import com.bssmh.portfolio.web.user.service.FindUserService;
import com.bssmh.portfolio.web.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final FindUserService findUserService;

}
