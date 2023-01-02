package com.bssmh.portfolio.aws;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AwsS3Dto {

    private String filePath;

    private String fileUid;

}
