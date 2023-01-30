package com.bssmh.portfolio.web.config.security.context;

import com.bssmh.portfolio.db.enums.MemberRoleType;
import lombok.Getter;

@Getter
public class MemberContext {
    private final String email;
    private final String registrationId;
    private final MemberRoleType role;

    private MemberContext(String email, String registrationId, MemberRoleType role) {
        this.email = email;
        this.registrationId = registrationId;
        this.role = role;
    }

    public static MemberContext create(String email, String registrationId, MemberRoleType role) {
        return new MemberContext(email, registrationId, role);
    }
}
