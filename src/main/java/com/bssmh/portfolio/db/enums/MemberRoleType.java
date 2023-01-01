package com.bssmh.portfolio.db.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberRoleType {

    ROLE_ADMIN("ROLE_ADMIN", "관리자"),
    ROLE_NORMAL("ROLE_NORMAL", "일반");

    private final String name;
    private final String description;

}
