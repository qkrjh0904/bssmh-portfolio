package com.bssmh.portfolio.db.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UploadDateType {

    AN_HOUR_AGO("업로드 날짜"),
    TODAY("조회수"),
    THIS_WEEK("좋아요 수"),
    THIS_MONTH("댓글 수"),
    THIS_YEAR("랭킹순");


    private final String name;

}
