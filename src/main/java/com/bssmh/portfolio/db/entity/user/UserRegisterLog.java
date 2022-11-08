package com.bssmh.portfolio.db.entity.user;

import com.bssmh.portfolio.db.entity.common.BaseCreateTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRegisterLog extends BaseCreateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_register_log_id")
    private Long id;

    private String email;

    private String name;

    private String locale;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder(access = AccessLevel.PRIVATE)
    private UserRegisterLog(String email, String name, String locale, User user) {
        this.email = email;
        this.name = name;
        this.locale = locale;
        this.user = user;
    }

    public static UserRegisterLog of(String email, String name, String locale, User user) {
        return UserRegisterLog.builder()
                .email(email)
                .name(name)
                .locale(locale)
                .user(user)
                .build();
    }

}
