package com.bssmh.portfolio.web.domain.file.service;

import com.bssmh.portfolio.web.domain.dto.AttachFileDto;
import com.bssmh.portfolio.web.domain.file.controller.rs.UploadFileRs;
import com.bssmh.portfolio.web.exception.AwsS3FileUploadException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class FileService {

    private final AwsS3Service awsS3Service;
    private final AttachFileService attachFileService;

    public UploadFileRs uploadFile(MultipartFile file) {
        AttachFileDto attachFileDto;

        try {
            attachFileDto = awsS3Service.upload(file);
        } catch (IOException e) {
            throw new AwsS3FileUploadException();
        }

        if (Objects.isNull(attachFileDto)) {
            throw new AwsS3FileUploadException();
        }

        attachFileService.save(attachFileDto);
        return UploadFileRs.create(attachFileDto.getFileUid(), attachFileDto.getFilePath());
    }
}
