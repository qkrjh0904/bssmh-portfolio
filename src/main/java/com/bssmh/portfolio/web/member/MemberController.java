package com.bssmh.portfolio.web.member;

import com.bssmh.portfolio.web.path.ApiPath;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    @PostMapping(ApiPath.TEST)
    public void test(){
        System.out.println("hello");
    }

}
