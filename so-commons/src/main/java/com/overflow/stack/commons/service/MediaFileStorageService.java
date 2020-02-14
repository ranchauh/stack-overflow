package com.overflow.stack.commons.service;

import com.overflow.stack.commons.model.UploadFileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface MediaFileStorageService {

    UploadFileResponse uploadFile(Long answerId,MultipartFile multipartFile) throws IOException;


    InputStream downloadFile(Long answerId, String imageId);
}
