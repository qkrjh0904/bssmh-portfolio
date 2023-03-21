package com.bssmh.portfolio.web.domain.dto;

import com.bssmh.portfolio.db.entity.attachfile.AttachFile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AttachFileDto {

    @Schema(description = "파일 명")
    private String fileName;

    @Schema(description = "파일 경로")
    private String filePath;

    @Schema(description = "파일 UID")
    private String fileUid;

    @Schema(description = "파일 크기")
    private Long fileSize;

    public static AttachFileDto create(String fileName, String filePath, String fileUid, Long fileSize) {
        AttachFileDto dto = new AttachFileDto();
        dto.fileName = fileName;
        dto.filePath = filePath;
        dto.fileUid = fileUid;
        dto.fileSize = fileSize;
        return dto;
    }

    public static AttachFileDto create(AttachFile attachFile) {
        AttachFileDto dto = new AttachFileDto();
        dto.fileName = attachFile.getFileName();
        dto.filePath = attachFile.getFilePath();
        dto.fileUid = attachFile.getFileUid();
        dto.fileSize = attachFile.getFileSize();
        return dto;
    }

}
