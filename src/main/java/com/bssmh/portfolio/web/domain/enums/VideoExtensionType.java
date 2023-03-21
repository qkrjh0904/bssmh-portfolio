package com.bssmh.portfolio.web.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VideoExtensionType {

    // 영상, 음성
    AVI("avi"), M4V("m4v"), MOV("mov"),
    MP3("mp3"), MP4("mp4"), WMV("wmv"),
    WEBM("webm");

    private final String extension;

}
