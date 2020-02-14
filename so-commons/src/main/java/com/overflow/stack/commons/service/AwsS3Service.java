package com.overflow.stack.commons.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;

import java.io.File;
import java.io.InputStream;

public interface AwsS3Service {

    PutObjectResult putObject(String bucketName, String keyName, InputStream inputStream, ObjectMetadata objectMetadata);

    S3Object getObject(String bucketName, String keyName);
}
