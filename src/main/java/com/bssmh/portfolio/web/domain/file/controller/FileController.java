package com.bssmh.portfolio.web.domain.file.controller;

import com.bssmh.portfolio.web.domain.file.service.FileService;
import com.bssmh.portfolio.web.domain.file.controller.rs.UploadFileRs;
import com.bssmh.portfolio.web.path.ApiPath;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "파일")
@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @Operation(summary = "파일 업로드")
    @PostMapping(ApiPath.FILE_UPLOAD)
    public UploadFileRs uploadFile(@RequestPart MultipartFile file) {
        return fileService.uploadFile(file);
    }

    @Operation(summary = "파일  다운로드")
    @GetMapping(ApiPath.FILE_DOWNLOAD)
    public ResponseEntity<byte[]> downloadFile(@PathVariable("file-uid") String fileUid) {
        return null;
    }

}
