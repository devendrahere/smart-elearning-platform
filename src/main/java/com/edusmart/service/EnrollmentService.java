package com.edusmart.service;

import com.edusmart.dto.CourseDTO;
import com.edusmart.dto.EnrollmentsDTO;
import com.edusmart.dto.UserDTO;

import java.util.List;

public interface EnrollmentService {
    EnrollmentsDTO enrollStudent(Long userId,Long courseId);
    boolean isStudentEnrolled(Long userId,Long courseId);
    void unenrollStudent(Long userId,Long courseId);
    List<CourseDTO> getCoursesByStudent(Long userId);
    List<UserDTO> getStudentsByCourse(Long courseId);
}
