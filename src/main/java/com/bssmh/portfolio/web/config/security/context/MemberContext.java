package com.bssmh.portfolio.web.config.security.context;

import lombok.Getter;

@Getter
public class MemberContext {
    private final String email;
    private final String role;

    private MemberContext(String email, String role) {
        this.email = email;
        this.role = role;
    }

    public static MemberContext create(String email, String role) {
        return new MemberContext(email, role);
    }
}
