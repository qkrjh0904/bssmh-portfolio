package com.bssmh.portfolio.web.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ImageExtensionType {
    //이미지
    JPG("jpg"), JPEG("jpeg"),
    BMP("bmp"), GIF("gif"),
    PSD("psd"), PDD("pdd"),
    TIF("tif"), PNG("png"),
    RAW("raw"),
    TIFF("tiff"),
    WEBP("webp");

    private final String extension;

}
