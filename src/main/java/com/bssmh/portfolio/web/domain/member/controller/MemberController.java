package com.bssmh.portfolio.web.domain.member.controller;

import com.bssmh.portfolio.web.domain.member.controller.rs.FindMemberRs;
import com.bssmh.portfolio.web.domain.member.controller.rs.FindMemberSelfRs;
import com.bssmh.portfolio.web.domain.member.service.FindMemberService;
import com.bssmh.portfolio.web.domain.member.service.MemberService;
import com.bssmh.portfolio.web.path.ApiPath;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "멤버")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final FindMemberService findMemberService;

    @Operation(summary = "내 계정 조회",
            description = "모든 데이터 제공")
    @GetMapping(ApiPath.MEMBER_SELF)
    public FindMemberSelfRs findMemberSelfRs() {
        return null;
    }

    @Operation(summary = "다른 멤버 조회",
            description = "일부 정보만 제공")
    @GetMapping(ApiPath.MEMBER_ID)
    public FindMemberRs findMemberRs(@PathVariable("member-id") Long memberId) {
        return null;
    }
}
