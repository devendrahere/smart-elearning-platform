package com.edusmart.repository;

import com.edusmart.entity.FileResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileResourceRepository extends JpaRepository<FileResource,Long> {

        List<FileResource> findByCoursesCourseId(Long courseID);
}
