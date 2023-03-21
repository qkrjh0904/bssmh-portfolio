package com.bssmh.portfolio.web.domain.file.controller;

import com.bssmh.portfolio.web.config.security.context.MemberContext;
import com.bssmh.portfolio.web.domain.file.controller.rs.UploadFileRs;
import com.bssmh.portfolio.web.domain.file.service.FileService;
import com.bssmh.portfolio.web.path.ApiPath;
import com.bssmh.portfolio.web.utils.RoleCheckUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static com.bssmh.portfolio.web.domain.enums.FileType.IMAGE;
import static com.bssmh.portfolio.web.domain.enums.FileType.VIDEO;

@Tag(name = "파일")
@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @Operation(summary = "이미지 업로드")
    @PostMapping(ApiPath.FILE_UPLOAD_IMAGE)
    public UploadFileRs uploadImage(@AuthenticationPrincipal MemberContext memberContext,
                                    @RequestPart MultipartFile file) {
        RoleCheckUtil.loginCheck(memberContext);
        return fileService.upload(file, IMAGE);
    }

    @Operation(summary = "영상 업로드")
    @PostMapping(ApiPath.FILE_UPLOAD_VIDEO)
    public UploadFileRs uploadFile(@AuthenticationPrincipal MemberContext memberContext,
                                   @RequestPart MultipartFile file) {
        RoleCheckUtil.loginCheck(memberContext);
        return fileService.upload(file, VIDEO);
    }

    @Operation(summary = "파일 다운로드 (이미지만 가능)")
    @GetMapping(ApiPath.FILE_DOWNLOAD)
    public ResponseEntity<byte[]> downloadFile(@PathVariable("file-uid") String fileUid) {
        return fileService.downloadFile(fileUid);
    }

}
