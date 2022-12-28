package com.bssmh.portfolio.web.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AttachFileDto {
    
    @Schema(description = "파일 명")
    private String fileName;
    
    @Schema(description = "파일 경로")
    private String filePath;
    
    @Schema(description = "파일 UID")
    private String fileUid;
    
    @Schema(description = "파일 크기")
    private Long fileSize;
    
}
