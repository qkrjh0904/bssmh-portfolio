package com.bssmh.portfolio.web.domain.file.service;

import com.bssmh.portfolio.aws.AwsS3Dto;
import com.bssmh.portfolio.aws.AwsS3Utils;
import com.bssmh.portfolio.web.domain.file.controller.rs.UploadFileRs;
import com.bssmh.portfolio.web.domain.file.exception.AwsS3FileUploadException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class FileService {

    public UploadFileRs uploadFile(MultipartFile file) {
        AwsS3Dto awsS3Dto;

        try {
            awsS3Dto = AwsS3Utils.upload(file);
        } catch (IOException e) {
            throw new AwsS3FileUploadException();
        }

        if (Objects.isNull(awsS3Dto)) {
            throw new AwsS3FileUploadException();
        }

        return UploadFileRs.create(awsS3Dto.getFileUid(), awsS3Dto.getFilePath());
    }
}
