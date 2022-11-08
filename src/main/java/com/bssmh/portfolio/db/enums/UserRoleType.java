package com.bssmh.portfolio.db.enums;

import lombok.Getter;

@Getter
public enum UserRoleType {

    ADMIN("ROLE_ADMIN", "관리자"),
    NORMAL("ROLE_NORMAL", "일반");

    private final String roleName;
    private final String description;

    UserRoleType(String roleName, String description) {
        this.roleName = roleName;
        this.description = description;
    }

}
