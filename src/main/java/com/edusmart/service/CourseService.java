package com.edusmart.service;

import com.edusmart.dto.CourseDTO;

import java.util.List;

public interface CourseService {
    CourseDTO createCourse(CourseDTO courseDTO);
    CourseDTO getCourseById(Long id);
    CourseDTO updateCourse(Long id,CourseDTO courseDTO);
    List<CourseDTO> getAllCourse();
    void deleteCourse(Long courseId);
}
