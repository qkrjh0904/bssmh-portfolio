package com.bssmh.portfolio.db.entity.user;

import com.bssmh.portfolio.db.entity.common.BaseTimeEntity;
import com.bssmh.portfolio.db.enums.UserRoleType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String name;

    private String sub;

    private String givenName;

    private String familyName;

    private Boolean emailVerified;

    private String locale;

    private String registrationId;

    private String clientId;

    private String clientSecret;

    @Enumerated(EnumType.STRING)
    private UserRoleType userRoleType;

    @Builder(access = AccessLevel.PRIVATE)
    private User(String email, String name, String sub, String givenName, String familyName, Boolean emailVerified,
                 String locale, String registrationId, String clientId, String clientSecret,
                 UserRoleType userRoleType) {
        this.email = email;
        this.name = name;
        this.sub = sub;
        this.givenName = givenName;
        this.familyName = familyName;
        this.emailVerified = emailVerified;
        this.locale = locale;
        this.registrationId = registrationId;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.userRoleType = userRoleType;
    }

    public static User of(String email, String name, String sub, String givenName, String familyName,
                          Boolean emailVerified, String locale, String registrationId, String clientId,
                          String clientSecret, UserRoleType userRoleType) {
        return User.builder()
                .email(email)
                .name(name)
                .sub(sub)
                .givenName(givenName)
                .familyName(familyName)
                .emailVerified(emailVerified)
                .locale(locale)
                .registrationId(registrationId)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .userRoleType(userRoleType)
                .build();
    }
}
