package com.edusmart.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class FileResourceDTO {
    private long fileId;
    @NotBlank(message = "Filename is required")
    private String fileName;
    @NotBlank(message = "FilePath is required")
    private String filePath;
    private LocalDateTime uploadAt;

    public long getFileId() {
        return fileId;
    }

    public void setFileId(long fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public LocalDateTime getUploadAt() {
        return uploadAt;
    }

    public void setUploadAt(LocalDateTime uploadAt) {
        this.uploadAt = uploadAt;
    }
}
