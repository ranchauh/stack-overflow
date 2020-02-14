package com.overflow.stack.commons.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UploadFileResponse {
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;

    UploadFileResponse(String fileName, String fileDownloadUri, String fileType, long size) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
    }

    public static UploadFileResponseBuilder builder() {
        return new UploadFileResponseBuilder();
    }

    public static class UploadFileResponseBuilder {
        private String fileName;
        private String fileDownloadUri;
        private String fileType;
        private long size;

        UploadFileResponseBuilder() {
        }

        public UploadFileResponseBuilder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public UploadFileResponseBuilder fileDownloadUri(String fileDownloadUri) {
            this.fileDownloadUri = fileDownloadUri;
            return this;
        }

        public UploadFileResponseBuilder fileType(String fileType) {
            this.fileType = fileType;
            return this;
        }

        public UploadFileResponseBuilder size(long size) {
            this.size = size;
            return this;
        }

        public UploadFileResponse build() {
            return new UploadFileResponse(fileName, fileDownloadUri, fileType, size);
        }

        public String toString() {
            return "UploadFileResponse.UploadFileResponseBuilder(fileName=" + this.fileName + ", fileDownloadUri=" + this.fileDownloadUri + ", fileType=" + this.fileType + ", size=" + this.size + ")";
        }
    }
}
