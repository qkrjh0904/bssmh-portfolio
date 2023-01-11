package com.bssmh.portfolio.web.domain.dto;

import com.bssmh.portfolio.db.entity.member.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberDto {

    @Schema(description = "멤버 id")
    private Long memberId;

    @Schema(description = "이름")
    private String name;

    @Schema(description = "프로필사진 url")
    private String profileImageUrl;

    @Schema(description = "이메일")
    private String email;

    public static MemberDto create(Member member) {
        MemberDto dto = new MemberDto();
        dto.memberId = member.getId();
        dto.name = member.getName();
        dto.profileImageUrl = member.getProfileImageUrl();
        dto.email = member.getEmail();
        return dto;
    }
}
