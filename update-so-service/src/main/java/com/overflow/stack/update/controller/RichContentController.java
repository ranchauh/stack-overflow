package com.overflow.stack.update.controller;

import com.overflow.stack.commons.model.UploadFileResponse;
import com.overflow.stack.update.entity.RichContent;
import com.overflow.stack.update.service.AnswerImageService;
import com.overflow.stack.update.service.persistence.RichContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RequestMapping(value = "api/v1/answers")
@RestController
public class RichContentController {

    @Autowired
    private AnswerImageService answerImageService;

    @Autowired
    private RichContentService richContentService;

    @PostMapping("/{answerId}/images")
    @ResponseStatus(HttpStatus.CREATED)
    public UploadFileResponse uploadImage(HttpServletRequest request,
                                          @PathVariable Long answerId,
                                          @RequestParam ("file") MultipartFile multipartFile) {
        UploadFileResponse response = answerImageService.uploadImage(answerId, multipartFile);
        response.setFileDownloadUri(request.getRequestURI()+"/"+response.getFileName());
        richContentService.save(answerId, RichContent.builder()
                .contentId(answerId+response.getFileName())
                .contentName(response.getFileName())
                .contentLocation(response.getFileDownloadUri())
                .contentType("IMAGE")
                .build());
        return response;
    }

    @GetMapping(value = "/{answerId}/images/{imageId}",
            produces = {MediaType.IMAGE_PNG_VALUE,MediaType.IMAGE_JPEG_VALUE})
    public @ResponseBody byte[] downloadFile(@PathVariable Long answerId,
                 @PathVariable String imageId,
                 HttpServletRequest request) {
        return answerImageService.downloadFile(answerId,imageId);
    }
}
