package com.bssmh.portfolio.web.config.security.context;

import com.bssmh.portfolio.db.enums.MemberRoleType;
import lombok.Getter;

@Getter
public class MemberContext {
    private final String email;
    private final MemberRoleType role;

    private MemberContext(String email, MemberRoleType role) {
        this.email = email;
        this.role = role;
    }

    public static MemberContext create(String email, MemberRoleType role) {
        return new MemberContext(email, role);
    }
}
