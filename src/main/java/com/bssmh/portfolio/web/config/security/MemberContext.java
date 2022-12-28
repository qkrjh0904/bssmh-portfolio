package com.bssmh.portfolio.web.config.security;

import lombok.Getter;

@Getter
public class MemberContext {
    private final String email;
    private final String role;

    private MemberContext(String email, String role) {
        this.email = email;
        this.role = role;
    }

    public static MemberContext of(String email, String role){
        return new MemberContext(email, role);
    }
}
