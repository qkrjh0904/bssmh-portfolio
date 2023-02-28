package com.bssmh.portfolio.web.domain.member.controller.rs;

import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.db.entity.member.MemberClassInfo;
import com.bssmh.portfolio.db.enums.MemberRoleType;
import com.bssmh.portfolio.db.enums.MemberType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.bssmh.portfolio.db.enums.MemberType.TEACHER;

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

    @Schema(description = "학년")
    private Integer schoolGrade;

    @Schema(description = "")
    private Integer schoolClass;

    @Schema(description = "")
    private Integer schoolNumber;

    @Schema(description = "입학년도")
    private Integer admissionYear;

    @Schema(description = "소속")
    private String belong;

    @Schema(description = "학생/선생님")
    private MemberType memberType;

    public static FindMemberSelfRs create(Member member) {
        MemberClassInfo memberClassInfo = getMemberClassInfo(member);
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
        rs.schoolGrade = Objects.isNull(memberClassInfo) ? null : memberClassInfo.getSchoolGrade();
        rs.schoolClass = Objects.isNull(memberClassInfo) ? null : memberClassInfo.getSchoolClass();
        rs.schoolNumber = Objects.isNull(memberClassInfo) ? null : memberClassInfo.getSchoolNumber();
        rs.admissionYear = member.getAdmissionYear();
        rs.belong = member.getBelong();
        rs.memberType = member.getMemberType();
        return rs;
    }

    private static MemberClassInfo getMemberClassInfo(Member member) {
        if (TEACHER.equals(member.getMemberType()) || ObjectUtils.isEmpty(member.getMemberClassInfoArrayList())) {
            return null;
        }

        List<MemberClassInfo> memberClassInfoList = member.getMemberClassInfoArrayList().stream()
                .sorted(Comparator.comparing(MemberClassInfo::getSchoolGrade).reversed())
                .collect(Collectors.toList());
        return memberClassInfoList.get(0);
    }
}
