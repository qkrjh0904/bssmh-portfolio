package com.bssmh.portfolio.db.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UploadDateType {

    AN_HOUR_AGO("1시간 전"),
    TODAY("오늘"),
    THIS_WEEK("이번주"),
    THIS_MONTH("이번달"),
    THIS_YEAR("올해");


    private final String name;

}
