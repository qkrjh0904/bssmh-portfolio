package com.bssmh.portfolio.web.domain.member.controller;

import com.bssmh.portfolio.web.domain.path.ApiPath;
import com.bssmh.portfolio.web.domain.member.controller.rq.UpdateMemberRq;
import com.bssmh.portfolio.web.domain.member.controller.rs.UpdateMemberRs;
import com.bssmh.portfolio.web.domain.member.service.FindMemberService;
import com.bssmh.portfolio.web.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final FindMemberService findMemberService;

    @PutMapping(ApiPath.MEMBER)
    public UpdateMemberRs updateUser(@AuthenticationPrincipal OAuth2User oAuth2User,
                                     @Validated @RequestBody UpdateMemberRq rq) {
        return memberService.updateUser(oAuth2User.getName(), rq);
    }

}