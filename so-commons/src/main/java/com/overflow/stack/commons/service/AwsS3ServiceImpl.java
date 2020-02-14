package com.overflow.stack.commons.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class AwsS3ServiceImpl implements AwsS3Service {

    @Autowired
    public AmazonS3 amazonS3;

    public PutObjectResult putObject(String bucketName, String keyName, InputStream inputStream, ObjectMetadata objectMetadata){
        return amazonS3.putObject(bucketName,keyName,inputStream, objectMetadata);
    }

    public S3Object getObject(String bucketName, String keyName){
        return amazonS3.getObject(bucketName,keyName);
    }
}
