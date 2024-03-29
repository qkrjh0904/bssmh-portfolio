package com.bssmh.portfolio.web.domain.member.controller;

import com.bssmh.portfolio.web.config.security.context.MemberContext;
import com.bssmh.portfolio.web.domain.member.controller.rq.SignupMemberRq;
import com.bssmh.portfolio.web.domain.member.controller.rq.UpdateMemberRq;
import com.bssmh.portfolio.web.domain.member.controller.rs.FindMemberListByNameRs;
import com.bssmh.portfolio.web.domain.member.controller.rs.FindMemberSelfRs;
import com.bssmh.portfolio.web.domain.member.controller.rs.FindOtherMemberRs;
import com.bssmh.portfolio.web.domain.member.service.FindMemberService;
import com.bssmh.portfolio.web.domain.member.service.MemberService;
import com.bssmh.portfolio.web.endpoint.ListResponse;
import com.bssmh.portfolio.web.path.ApiPath;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "멤버")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final FindMemberService findMemberService;

    @Operation(summary = "내 계정 조회", description = "모든 데이터 제공")
    @GetMapping(ApiPath.MEMBER_SELF)
    public FindMemberSelfRs findMemberSelf(@AuthenticationPrincipal MemberContext memberContext) {
        return findMemberService.findMemberSelf(memberContext);
    }

    @Operation(summary = "이름으로 멤버 조회")
    @GetMapping(ApiPath.MEMBER_NAME)
    public ListResponse<FindMemberListByNameRs> findMemberListByName(@AuthenticationPrincipal MemberContext memberContext,
                                                                     @RequestParam("name") String name) {
        return findMemberService.findMemberListByName(memberContext, name);
    }

    @Operation(summary = "다른 멤버 조회", description = "일부 정보만 제공")
    @GetMapping(ApiPath.MEMBER_ID)
    public FindOtherMemberRs findOtherMember(@AuthenticationPrincipal MemberContext memberContext,
                                             @PathVariable("member-id") Long memberId) {
        return findMemberService.findOtherMember(memberContext, memberId);
    }

    @Operation(summary = "멤버 정보 수정")
    @PutMapping(ApiPath.MEMBER)
    public void updateMember(@AuthenticationPrincipal MemberContext memberContext,
                             @Validated @RequestBody UpdateMemberRq rq) {
        memberService.updateMember(memberContext, rq);
    }

    @Operation(summary = "멤버 회원가입", description = "회원가입 하는 순간 동의항목 추가됨")
    @PostMapping(ApiPath.MEMBER_SIGNUP)
    public void signupMember(@AuthenticationPrincipal MemberContext memberContext,
                             @Validated @RequestBody SignupMemberRq rq) {
        memberService.signupMember(memberContext, rq);
    }

}
