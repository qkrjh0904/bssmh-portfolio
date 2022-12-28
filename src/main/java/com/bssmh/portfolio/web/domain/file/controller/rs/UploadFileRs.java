package com.bssmh.portfolio.web.domain.file.controller.rs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UploadFileRs {

    @Schema(description = "파일 UID")
    private String fileUid;

    @Schema(description = "파일 명")
    private String fileName;

}
