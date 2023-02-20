package com.bssmh.portfolio.db.entity.member;

import com.bssmh.portfolio.db.entity.comment.Comment;
import com.bssmh.portfolio.db.entity.common.BaseTimeEntity;
import com.bssmh.portfolio.db.entity.follow.Follow;
import com.bssmh.portfolio.db.entity.portfolio.Portfolio;
import com.bssmh.portfolio.db.enums.MemberRoleType;
import com.bssmh.portfolio.db.enums.MemberType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_email_registration_id_on_member",
                        columnNames = {"email", "registrationId"})
        }
)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String email;

    private String name;

    private String nickName;

    @Column(columnDefinition = "text")
    private String profileImageUrl;

    @Column(nullable = false)
    private String registrationId;

    @Enumerated(EnumType.STRING)
    private MemberRoleType memberRoleType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberType memberType;

    @Column(columnDefinition = "text")
    private String description;

    private String phone;

    private String job;

    private String belong;

    private LocalDate admissionDate;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Portfolio> portfolioList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "fromMember", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Follow> fromMemberList = new ArrayList<>();

    @OneToMany(mappedBy = "toMember", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Follow> toMemberList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberAgreement> memberAgreementList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberClassInfo> memberClassInfoArrayList = new ArrayList<>();

    @Builder(access = AccessLevel.PRIVATE)
    private Member(String email, String name, String nickName, String registrationId,
                   String profileImageUrl, MemberRoleType memberRoleType, MemberType memberType) {
        this.email = email;
        this.name = name;
        this.nickName = nickName;
        this.registrationId = registrationId;
        this.profileImageUrl = profileImageUrl;
        this.memberRoleType = memberRoleType;
        this.memberType = memberType;
    }

    public static Member ofNormal(String email, String name, String profileImageUrl, String registrationId) {
        return Member.builder()
                .email(email)
                .name(name)
                .nickName(name)
                .profileImageUrl(profileImageUrl)
                .registrationId(registrationId)
                .memberRoleType(MemberRoleType.ROLE_NORMAL)
                .memberType(MemberType.EMPTY)
                .build();
    }

    public void update(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void update(String nickName, String description, String phone, String job,
                       MemberType memberType, String belong, String admissionDate) {
        this.nickName = nickName;
        this.description = description;
        this.phone = phone;
        this.job = job;
        this.memberType = memberType;
        this.belong = belong;
        this.admissionDate = Objects.nonNull(admissionDate) ?
                LocalDate.parse(admissionDate, DateTimeFormatter.ISO_LOCAL_DATE) : this.admissionDate;
    }

    public void update(MemberType memberType, String phone, String belong, String admissionDate) {
        this.memberType = memberType;
        this.phone = phone;
        this.belong = belong;
        this.admissionDate = Objects.nonNull(admissionDate) ?
                LocalDate.parse(admissionDate, DateTimeFormatter.ISO_LOCAL_DATE) : this.admissionDate;
    }
}
