package com.bssmh.portfolio.web.domain.file.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.bssmh.portfolio.db.entity.attachfile.AttachFile;
import com.bssmh.portfolio.web.domain.dto.AttachFileDto;
import com.bssmh.portfolio.web.domain.file.controller.rs.UploadFileRs;
import com.bssmh.portfolio.web.exception.AwsS3FileUploadException;
import com.bssmh.portfolio.web.exception.FailToS3DownloadException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<byte[]> downloadFile(String fileUid) {
        try {
            AttachFile attachFile = attachFileService.findByFileUid(fileUid);
            S3Object s3Object = awsS3Service.getS3Object(attachFile);
            S3ObjectInputStream objectContent = s3Object.getObjectContent();
            ObjectMetadata objectMetadata = s3Object.getObjectMetadata();
            HttpHeaders httpHeaders = getHttpHeaders(objectMetadata, attachFile);
            final byte[] bytes = IOUtils.toByteArray(objectContent);
            return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
        } catch (IOException e) {
            throw new FailToS3DownloadException();
        }
    }

    private HttpHeaders getHttpHeaders(ObjectMetadata objectMetadata, AttachFile attachFile) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType(objectMetadata.getContentType()));
        httpHeaders.setContentLength(objectMetadata.getContentLength());
        httpHeaders.setLastModified(objectMetadata.getLastModified().getTime());
        httpHeaders.setCacheControl("max-get=604800");
        httpHeaders.set("file-name", attachFile.getFileName());
        return httpHeaders;
    }

}
