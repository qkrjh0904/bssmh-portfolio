package com.bssmh.portfolio.web.domain.file.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.transfer.Transfer;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import com.bssmh.portfolio.web.domain.dto.AttachFileDto;
import com.bssmh.portfolio.web.utils.FileValidateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@Component
public class AwsS3Service {

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;
    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;
    @Value("${cloud.aws.region.static}")
    private String region;
    @Value("${bssmh.s3.bucket-name}")
    private String bucketName;

    public TransferManager getTransferManger() {
        return TransferManagerBuilder.standard()
                .withS3Client(getAmazonS3())
                .build();
    }

    private AmazonS3 getAmazonS3() {
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretKey);
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .withRegion(region)
                .build();
    }

    public AttachFileDto upload(@NotNull MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        Long fileSize = file.getSize();
        String extension = getExtension(originalFilename);
        FileValidateUtils.validationCheck(extension);
        String filePath = getFilePath();
        String s3Path = bucketName + filePath;
        String fileUid = getFileUid(extension);
        ObjectMetadata objectMetadata = getObjectMetadata(file);
        TransferManager tm = getTransferManger();

        Upload upload = tm.upload(s3Path, fileUid, file.getInputStream(), objectMetadata);
        waitForCompletion(upload);
        tm.shutdownNow();

        return AttachFileDto.create(originalFilename, filePath, fileUid, fileSize);
    }

    private ObjectMetadata getObjectMetadata(MultipartFile file) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());
        return objectMetadata;
    }

    private String getFilePath() {
        LocalDate now = LocalDate.now();
        String year = File.separator + now.getYear();
        String month = File.separator + now.getMonth().getValue();
        String day = File.separator + now.getDayOfMonth();
        return (year + month + day).replace("\\", "/");
    }

    private void waitForCompletion(Transfer transfer) {
        try {
            transfer.waitForCompletion();
        } catch (AmazonServiceException e) {
            log.error("Amazon S3 service error: " + e.getMessage());
        } catch (AmazonClientException e) {
            log.error("Amazon S3 client error: " + e.getMessage());
        } catch (InterruptedException e) {
            log.error("Amazon S3 transfer interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    private String getExtension(String originalFilename) {
        return FilenameUtils.getExtension(originalFilename);
    }

    private String getFileUid(String extension) {
        return UUID.randomUUID() + "." + extension;
    }


}
