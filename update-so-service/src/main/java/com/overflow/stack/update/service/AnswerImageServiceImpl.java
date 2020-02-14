package com.overflow.stack.update.service;

import com.amazonaws.util.IOUtils;
import com.overflow.stack.commons.exception.FileNotFoundException;
import com.overflow.stack.commons.exception.FileStorageException;
import com.overflow.stack.commons.model.UploadFileResponse;
import com.overflow.stack.commons.service.MediaFileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class AnswerImageServiceImpl implements AnswerImageService {

    @Autowired
    private MediaFileStorageService mediaFileStorageService;

    @Override
    public UploadFileResponse uploadImage(Long answerId,MultipartFile multipartFile) {
        try {
            return mediaFileStorageService.uploadFile(answerId,multipartFile);
        } catch (IOException e) {
            throw new FileStorageException("Error while uploading file");
        }
    }

    @Override
    public byte[] downloadFile(Long answerId, String imageId) {
        try {
            return IOUtils.toByteArray(mediaFileStorageService.downloadFile(answerId,imageId));
        } catch (IOException e) {
            throw new FileNotFoundException(String.format("Image with id %s not found",imageId));
        }
    }
}
