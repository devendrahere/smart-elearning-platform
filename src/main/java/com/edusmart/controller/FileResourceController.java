package com.edusmart.controller;

import com.edusmart.dto.FileResourceDTO;
import com.edusmart.service.FileResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileResourceController {

    @Autowired
    private FileResourceService fileResourceService;

    //adding file to database
    //url /api/files/course/{courseId}/upload
    @PostMapping("/course/{courseId}/upload")
    public ResponseEntity<FileResourceDTO> uploadFile(@PathVariable Long courseId, @RequestParam("file") MultipartFile file){
        return ResponseEntity.status(HttpStatus.CREATED).body(fileResourceService.uploadFile(courseId,file));
    }

    //fetching the file by file id
    //url /api/files/{fileId}
    @GetMapping("/{fileId}")
    public ResponseEntity<FileResourceDTO> getFileById(@PathVariable Long fileId){
        return ResponseEntity.ok(fileResourceService.getFileById(fileId));
    }

    //getting all files under a course
    //url /api/files/course/{courseId}
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<FileResourceDTO>> getAllFilesByCourse(@PathVariable Long courseId){
        return ResponseEntity.ok(fileResourceService.getAllFilesByCourse(courseId));
    }

    //deleting file by fileId
    //url /api/files/{fileId}
    @DeleteMapping("/{fileId}")
    public ResponseEntity<Void> deleteFile(@PathVariable Long fileId){
        fileResourceService.deleteFile(fileId);
        return ResponseEntity.noContent().build();
    }

    //to fetch the file by fileId
    //url /api/files/{fileId}/download
    @GetMapping("/{fileId}/download")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId){
        return ResponseEntity.ok(fileResourceService.downloadFile(fileId));
    }
}
