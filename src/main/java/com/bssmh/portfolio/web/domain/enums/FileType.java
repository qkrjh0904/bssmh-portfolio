package com.bssmh.portfolio.web.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileType {

    VIDEO("video"),
    IMAGE("image");

    private final String type;

}
