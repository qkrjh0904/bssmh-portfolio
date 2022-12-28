package com.bssmh.portfolio.db.entity.member;

import com.bssmh.portfolio.db.entity.comment.Comment;
import com.bssmh.portfolio.db.entity.common.BaseTimeEntity;
import com.bssmh.portfolio.db.entity.contributor.Contributor;
import com.bssmh.portfolio.db.entity.portfolio.Portfolio;
import com.bssmh.portfolio.db.enums.MemberRoleType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity implements OAuth2User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String name;

    private String nickName;

    private String profileImage;

    private String registrationId;

    @Enumerated(EnumType.STRING)
    private MemberRoleType memberRoleType;

    private String description;

    private String phone;

    @OneToMany(mappedBy = "member")
    private List<Portfolio> portfolioList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> commentList = new ArrayList<>();

    @Builder(access = AccessLevel.PRIVATE)
    private Member(String email, String name, String nickName, String registrationId,
                   String profileImage, MemberRoleType memberRoleType) {
        this.email = email;
        this.name = name;
        this.nickName = nickName;
        this.registrationId = registrationId;
        this.profileImage = profileImage;
        this.memberRoleType = memberRoleType;
    }

    public static Member ofNormal(String email, String name, String picture, String registrationId) {
        return Member.builder()
                .email(email)
                .name(name)
                .nickName(name)
                .profileImage(picture)
                .registrationId(registrationId)
                .memberRoleType(MemberRoleType.NORMAL)
                .build();
    }

    public void update(String name, String picture) {
        this.name = name;
        this.profileImage = picture;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}
