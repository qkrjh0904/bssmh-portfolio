package com.bssmh.portfolio.db.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SearchType {

    TITLE("제목"),
    THEME("테마"),
    CONTRIBUTOR("제작자"),
    STUDENT("학생");


    private final String name;

}
