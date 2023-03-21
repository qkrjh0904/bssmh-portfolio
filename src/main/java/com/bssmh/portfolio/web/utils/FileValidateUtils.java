package com.bssmh.portfolio.web.utils;

import com.bssmh.portfolio.web.domain.enums.FileType;
import com.bssmh.portfolio.web.domain.enums.ImageExtensionType;
import com.bssmh.portfolio.web.domain.enums.VideoExtensionType;
import com.bssmh.portfolio.web.exception.NotMatchedExtensionException;
import lombok.experimental.UtilityClass;

import java.util.Arrays;

import static com.bssmh.portfolio.web.domain.enums.FileType.IMAGE;

@UtilityClass
public class FileValidateUtils {

    public static void validationCheck(String extension, FileType fileType) {
        if (isMatched(extension, fileType)) {
            return;
        }

        throw new NotMatchedExtensionException(extension);
    }

    private static boolean isMatched(String extension, FileType fileType) {
        if (IMAGE.equals(fileType)) {
            return Arrays.stream(ImageExtensionType.values())
                    .anyMatch(it -> extension.equalsIgnoreCase(it.getExtension()));
        }

        return Arrays.stream(VideoExtensionType.values())
                .anyMatch(it -> extension.equalsIgnoreCase(it.getExtension()));
    }

}
