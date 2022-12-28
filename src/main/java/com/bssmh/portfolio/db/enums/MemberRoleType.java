package com.bssmh.portfolio.db.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberRoleType {

    ADMIN("ROLE_ADMIN", "관리자"),
    NORMAL("ROLE_NORMAL", "일반");

    private final String roleName;
    private final String description;

}
