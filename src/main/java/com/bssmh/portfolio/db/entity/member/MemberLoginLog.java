package com.bssmh.portfolio.db.entity.member;

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
public class MemberLoginLog extends BaseCreateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_login_log_id")
    private Long id;

    private String email;

    private String name;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder(access = AccessLevel.PRIVATE)
    private MemberLoginLog(String email, String name, Member member) {
        this.email = email;
        this.name = name;
        this.member = member;
    }

    public static MemberLoginLog of(String email, String name, Member member) {
        return MemberLoginLog.builder()
                .email(email)
                .name(name)
                .member(member)
                .build();
    }
}
