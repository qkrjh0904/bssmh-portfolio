package com.bssmh.portfolio.web.domain.file.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.transfer.Transfer;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import com.bssmh.portfolio.db.entity.attachfile.AttachFile;
import com.bssmh.portfolio.web.domain.dto.AttachFileDto;
import com.bssmh.portfolio.web.domain.enums.FileType;
import com.bssmh.portfolio.web.exception.NoSuchS3AttachFileException;
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
import java.util.Objects;
import java.util.UUID;

import static com.bssmh.portfolio.web.domain.enums.FileType.IMAGE;

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
    @Value("${bssmh.s3.video-bucket-name}")
    private String videoBucketName;

    private final String TMP_DIR_PATH = System.getProperty("java.io.tmpdir");

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

    public AttachFileDto upload(@NotNull MultipartFile file, FileType fileType) throws IOException {
        String originalFilename = file.getOriginalFilename();
        Long fileSize = file.getSize();
        String extension = getExtension(originalFilename);
        FileValidateUtils.validationCheck(extension, fileType);
        String filePath = getFilePath();
        String s3Path = getS3Path(fileType, filePath);
        String fileUid = getFileUid(extension);
        ObjectMetadata objectMetadata = getObjectMetadata(file);
        TransferManager tm = getTransferManger();

        Upload upload = tm.upload(s3Path, fileUid, file.getInputStream(), objectMetadata);
        waitForCompletion(upload);
        tm.shutdownNow();

        return AttachFileDto.create(originalFilename, filePath, fileUid, fileSize);
    }

    private String getS3Path(FileType fileType, String filePath) {
        if (IMAGE.equals(fileType)) {
            return bucketName + filePath;
        }
        return videoBucketName + filePath;
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

    private String getDownloadPath(String fileUid) {
        return TMP_DIR_PATH + File.separator + fileUid;
    }

    public S3Object getS3Object(AttachFile attachFile) {
        String fileUid = attachFile.getFileUid();
        String s3Path = getS3Path(IMAGE, attachFile.getFilePath());
        AmazonS3 amazonS3 = getAmazonS3();
        S3Object s3Object = amazonS3.getObject(new GetObjectRequest(s3Path, fileUid));
        if (Objects.isNull(s3Object)) {
            throw new NoSuchS3AttachFileException(fileUid);
        }
        return s3Object;
    }

}
