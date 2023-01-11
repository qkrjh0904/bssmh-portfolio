package com.bssmh.portfolio.web.utils;

import com.bssmh.portfolio.web.domain.enums.ExtensionType;
import com.bssmh.portfolio.web.exception.NotMatchedExtensionException;
import lombok.experimental.UtilityClass;

import java.util.Arrays;

@UtilityClass
public class FileValidateUtils {

    public static void validationCheck(String extension) {
        if (isMatched(extension)) {
            return;
        }

        throw new NotMatchedExtensionException(extension);
    }

    private static boolean isMatched(String extension) {
        return Arrays.stream(ExtensionType.values())
                .anyMatch(it -> extension.equalsIgnoreCase(it.getExtension()));
    }

}
