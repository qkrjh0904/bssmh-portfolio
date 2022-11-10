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

    private String registrationId;

    private String clientId;

    private String clientSecret;

    @Enumerated(EnumType.STRING)
    private UserRoleType userRoleType;

    @Builder(access = AccessLevel.PRIVATE)
    private User(String email, String name, String registrationId, String clientId, String clientSecret,
                 UserRoleType userRoleType) {
        this.email = email;
        this.name = name;
        this.registrationId = registrationId;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.userRoleType = userRoleType;
    }

    public static User ofNormal(String email, String name, String registrationId, String clientId, String clientSecret) {
        return User.builder()
                .email(email)
                .name(name)
                .registrationId(registrationId)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .userRoleType(UserRoleType.NORMAL)
                .build();
    }

    public static User ofAdmin(String email, String name, String registrationId, String clientId, String clientSecret) {
        return User.builder()
                .email(email)
                .name(name)
                .registrationId(registrationId)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .userRoleType(UserRoleType.ADMIN)
                .build();
    }
}
