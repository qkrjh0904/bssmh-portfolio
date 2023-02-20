package com.bssmh.portfolio.web.domain.member.controller.rs;

import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.db.enums.MemberRoleType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FindMemberSelfRs {

    @Schema(description = "멤버 id")
    private Long memberId;

    @Schema(description = "이름")
    private String name;

    @Schema(description = "프로필사진 url")
    private String profileImageUrl;

    @Schema(description = "이메일")
    private String email;

    @Schema(description = "전화번호")
    private String phone;

    @Schema(description = "소개")
    private String description;

    @Schema(description = "멤버 역할")
    private MemberRoleType memberRoleType;

    @Schema(description = "직군, 직무, 작업 등")
    private String job;

    @Schema(description = "포트폴리오 수")
    private Integer portfolioCount;

    @Schema(description = "팔로워 수")
    private Integer followerCount;

    @Schema(description = "팔로잉 수")
    private Integer followingCount;
    
    public static FindMemberSelfRs create(Member member) {
        FindMemberSelfRs rs = new FindMemberSelfRs();
        rs.memberId = member.getId();
        rs.name = member.getName();
        rs.profileImageUrl = member.getProfileImageUrl();
        rs.email = member.getEmail();
        rs.phone = member.getPhone();
        rs.description = member.getDescription();
        rs.memberRoleType = member.getMemberRoleType();
        rs.job = member.getJob();
        rs.portfolioCount = member.getPortfolioList().size();
        rs.followerCount = member.getToMemberList().size();
        rs.followingCount = member.getFromMemberList().size();
        return rs;
    }
}
