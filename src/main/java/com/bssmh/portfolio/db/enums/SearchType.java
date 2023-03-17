package com.bssmh.portfolio.db.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SearchType {

    TITLE("제목"),
    THEME("테마"),
    CREATOR("제작자"),
    CONTRIBUTOR("기여자"),
    CREATOR_AND_CONTRIBUTOR("제작자+기여자");


    private final String name;

}
