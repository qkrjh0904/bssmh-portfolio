package com.bssmh.portfolio.web.domain.member.controller.rs;

import com.bssmh.portfolio.db.entity.member.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FindMemberListByNameRs {

    @Schema(description = "멤버 id")
    private Long memberId;

    @Schema(description = "이름")
    private String name;

    @Schema(description = "이메일")
    private String email;

    public static FindMemberListByNameRs create(Member member) {
        FindMemberListByNameRs rs = new FindMemberListByNameRs();
        rs.memberId = member.getId();
        rs.name = member.getName();
        rs.email = member.getEmail();
        return rs;
    }
}
