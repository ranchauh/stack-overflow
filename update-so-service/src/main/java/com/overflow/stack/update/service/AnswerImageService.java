package com.overflow.stack.update.service;

import com.overflow.stack.commons.model.UploadFileResponse;
import org.springframework.web.multipart.MultipartFile;

public interface AnswerImageService {

    UploadFileResponse uploadImage(Long answerId,MultipartFile multipartFile);

    byte[] downloadFile(Long answerId, String imageId);
}
