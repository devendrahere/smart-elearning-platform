package com.edusmart.service.implemeted;

import com.edusmart.dto.FileResourceDTO;
import com.edusmart.entity.Courses;
import com.edusmart.entity.FileResource;
import com.edusmart.repository.CourseRepository;
import com.edusmart.repository.FileResourceRepository;
import com.edusmart.service.FileResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FileResourceServiceImple implements FileResourceService {

    @Autowired
    private FileResourceRepository fileResourceRepository;

    @Autowired
    private CourseRepository courseRepository;

    private final String storageDirectory="uploads/";


    @Override
    public FileResourceDTO uploadFile(Long courseId, MultipartFile file) {
        Courses course = courseRepository.findById(courseId)
                .orElseThrow(()->new RuntimeException("Course not found with id : "+courseId));

        try {
            Files.createDirectories(Paths.get(storageDirectory));
        }
        catch (IOException e){
            throw  new RuntimeException("Could not create uploads directory");
        }

        String originalFileName=file.getOriginalFilename();
        String uniqueName= UUID.randomUUID()+"_"+originalFileName;
        Path filepath=Paths.get(storageDirectory,uniqueName);

        try {
            Files.write(filepath, file.getBytes());
        }
        catch (IOException e){
            throw  new RuntimeException("Error saving file : "+originalFileName+" "+e.getMessage());
        }

        FileResource entity= new FileResource();
        entity.setFileName(file.getOriginalFilename());
        entity.setFilePath(filepath.toString());
        entity.setCourses(course);
        entity.setUploadAt(LocalDateTime.now());

        FileResource saved= fileResourceRepository.save(entity);

        return mapToDTO(saved);
    }

    @Override
    public FileResourceDTO getFileById(Long fileId) {

        FileResource file= fileResourceRepository.findById(fileId)
                .orElseThrow(()->new RuntimeException("file not found with id : "+fileId));
        return mapToDTO(file);
    }

    @Override
    public List<FileResourceDTO> getAllFilesByCourse(Long courseId) {
        return fileResourceRepository.findByCoursesCourseId(courseId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteFile(Long fileId) {
        FileResource file = fileResourceRepository.findById(fileId)
                .orElseThrow(()->new RuntimeException("File not found !"));

        try{
            Files.deleteIfExists(Path.of(file.getFilePath()));
        }catch (IOException e){
            throw new RuntimeException("Error deleting file "+e.getMessage());
        }

        fileResourceRepository.delete(file);
    }

    @Override
    public Resource downloadFile(Long fileId) {
        FileResource file = fileResourceRepository.findById(fileId)
                .orElseThrow(()->new RuntimeException("File not found !"));

        File localFile=new File(file.getFilePath());
        if(!localFile.exists()){
            throw  new RuntimeException("File not found on disk");
        }


        return new FileSystemResource(localFile);
    }

    //mapper helper

    private  FileResourceDTO mapToDTO(FileResource entity){
        FileResourceDTO dto= new FileResourceDTO();

        dto.setFileId(entity.getFileId());
        dto.setFileName(entity.getFileName());
        dto.setFilePath(entity.getFilePath());
        dto.setUploadAt(entity.getUploadAt());

        return dto;
    }
}
