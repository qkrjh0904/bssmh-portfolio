package com.bssmh.portfolio.web.security;

import com.bssmh.portfolio.db.entity.member.Member;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String email;
    private String name;
    private String profileImage;

    public SessionUser(Member member) {
        this.email = member.getEmail();
        this.name = member.getName();
        this.profileImage = member.getProfileImage();
    }
}
