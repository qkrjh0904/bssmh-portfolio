package com.bssmh.portfolio.aws;

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
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@UtilityClass
public class AwsS3Utils {

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;
    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;
    @Value("${cloud.aws.region.static}")
    private String region;
    @Value("${bssmh.s3.bucket-name}")
    private String bucketName;

    public static TransferManager getTransferManger() {
        return TransferManagerBuilder.standard()
                .withS3Client(getAmazonS3())
                .build();
    }

    private static AmazonS3 getAmazonS3() {
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretKey);
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .withRegion(region)
                .build();
    }

    public static AwsS3Dto upload(@NotNull MultipartFile file) throws IOException {
        String filePath = getFilePath();
        String s3Path = bucketName + filePath;
        String extension = getExtension(file);
        String fileUid = getFileUid(extension);
        ObjectMetadata objectMetadata = getObjectMetadata(file);
        TransferManager tm = getTransferManger();

        Upload upload = tm.upload(s3Path, fileUid, file.getInputStream(), objectMetadata);
        waitForCompletion(upload);
        tm.shutdownNow();

        return AwsS3Dto.builder()
                .fileUid(fileUid)
                .filePath(filePath)
                .build();
    }

    private static ObjectMetadata getObjectMetadata(MultipartFile file) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());
        return objectMetadata;
    }

    private static String getFilePath() {
        LocalDate now = LocalDate.now();
        String year = File.separator + now.getYear();
        String month = File.separator + now.getMonth().getValue();
        String day = File.separator + now.getDayOfMonth();
        return (year + month + day).replace("\\", "/");
    }

    private static void waitForCompletion(Transfer transfer) {
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

    private static String getExtension(MultipartFile file) {
        return FilenameUtils.getExtension(file.getOriginalFilename());
    }

    private static String getFileUid(String extension) {
        return UUID.randomUUID() + "." + extension;
    }


}
