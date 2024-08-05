package com.mihai.Java_2024.s3config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Log4j2
public class S3Service {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.region}")
    private String region;

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFile(String timestamp, MultipartFile file) throws IOException {

        File tempFile = File.createTempFile("upload-", file.getOriginalFilename());

        file.transferTo(tempFile);

        String fileName;

        fileName = timestamp + file.getOriginalFilename();

        log.info("Uploading file: " + fileName);

        PutObjectRequest putObjectRequest =  PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .acl(ObjectCannedACL.PUBLIC_READ)
                .build();
        s3Client.putObject(putObjectRequest, RequestBody.fromFile(tempFile));

        return getFileUrl(fileName);



    }

    private String getFileUrl(String fileName) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, fileName);
    }

    public void deleteFile(String fileName) {
        s3Client.deleteObject(
                DeleteObjectRequest.builder()
                        .bucket(bucketName)
                        .key(fileName)
                        .build()
        );
    }

    public ResponseInputStream<?> getFile(String fileName) {
        return s3Client.getObject(GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build());
    }

}