package com.edusmart.service;

import com.edusmart.dto.FileResourceDTO;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileResourceService {
    FileResourceDTO uploadFile(Long courseId, MultipartFile file);
    FileResourceDTO getFileById(Long fileId);
    List<FileResourceDTO> getAllFilesByCourse(Long courseId);
    void deleteFile(Long fileId);
    Resource downloadFile(Long fileId);
}
