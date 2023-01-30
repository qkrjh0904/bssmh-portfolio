package com.bssmh.portfolio.web.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExtensionType {

    // 영상, 음성
    AVI("avi"), M4V("m4v"), MOV("mov"),
    MP3("mp3"), MP4("mp4"), WMV("wmv"),
    WEBM("webm"),

    //이미지
    JPG("jpg"), JPEG("jpeg"),
    BMP("bmp"), GIF("gif"),
    PSD("psd"), PDD("pdd"),
    TIF("tif"), PNG("png"),
    RAW("raw"),
    TIFF("tiff"),
    WEBP("webp")
    ;

    private final String extension;

}
