package com.overflow.stack.commons.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.overflow.stack.commons.exception.FileStorageException;
import com.overflow.stack.commons.model.UploadFileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class MediaFileStorageServiceImpl implements MediaFileStorageService {

    @Autowired
    private AwsS3Service awsS3Service;

    @Value("${image.file.storage.s3.bucket.name:}")
    private String bucketName;

    public UploadFileResponse uploadFile(Long answerId,MultipartFile multipartFile) throws IOException {
        // Normalize file name
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        // Replace white spaces with "-"
        fileName = StringUtils.replace(fileName," ","-");
        // Check if the file's name contains invalid characters
        if(fileName.contains("..")) {
            throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
        }
        ObjectMetadata objectMetadata = new ObjectMetadata();
        String keyName = answerId + "/" + fileName;
        PutObjectResult result = awsS3Service.putObject(bucketName, keyName, multipartFile.getInputStream(), objectMetadata);
        return UploadFileResponse.builder()
                .fileName(fileName)
                .fileType(result.getMetadata().getContentType())
                .size(multipartFile.getSize())
                .build();
    }

    @Override
    public InputStream downloadFile(Long answerId, String imageId) {
        S3Object s3Object = awsS3Service.getObject(bucketName, answerId + "/" + imageId);
        return s3Object.getObjectContent().getDelegateStream();
    }


}
