package com.bssmh.portfolio.db.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberType {

    STUDENT("학생"),
    TEACHER("선생님"),
    EMPTY("미지정");

    private final String name;

}
