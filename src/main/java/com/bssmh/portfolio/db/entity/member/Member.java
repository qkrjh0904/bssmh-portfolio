package com.bssmh.portfolio.db.entity.member;

import com.bssmh.portfolio.db.entity.comment.Comment;
import com.bssmh.portfolio.db.entity.common.BaseTimeEntity;
import com.bssmh.portfolio.db.entity.portfolio.Portfolio;
import com.bssmh.portfolio.db.enums.MemberRoleType;
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
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String name;

    private String nickName;

    @Column(columnDefinition = "text")
    private String profileImageUrl;

    private String registrationId;

    @Enumerated(EnumType.STRING)
    private MemberRoleType memberRoleType;

    @Column(columnDefinition = "text")
    private String description;

    private String phone;

    private String job;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Portfolio> portfolioList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @Builder(access = AccessLevel.PRIVATE)
    private Member(String email, String name, String nickName, String registrationId,
                   String profileImageUrl, MemberRoleType memberRoleType) {
        this.email = email;
        this.name = name;
        this.nickName = nickName;
        this.registrationId = registrationId;
        this.profileImageUrl = profileImageUrl;
        this.memberRoleType = memberRoleType;
    }

    public static Member ofNormal(String email, String name, String profileImageUrl, String registrationId) {
        return Member.builder()
                .email(email)
                .name(name)
                .nickName(name)
                .profileImageUrl(profileImageUrl)
                .registrationId(registrationId)
                .memberRoleType(MemberRoleType.ROLE_NORMAL)
                .build();
    }

    public void update(String name, String profileImageUrl) {
        this.name = name;
        this.profileImageUrl = profileImageUrl;
    }

}
