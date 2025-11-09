package com.edusmart.service.ui;

import com.edusmart.dto.CourseDTO;
import com.edusmart.dto.EnrollmentsDTO;
import com.edusmart.dto.UserDTO;
import com.edusmart.service.CourseService;
import com.edusmart.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UIEnrollmentService {
    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private CourseService courseService; // Add this

    public EnrollmentsDTO enrollStudent(Long userId, Long courseId) {
        return enrollmentService.enrollStudent(userId, courseId);
    }

    public boolean isStudentEnrolled(Long userId, Long courseId) {
        return enrollmentService.isStudentEnrolled(userId, courseId);
    }

    public List<CourseDTO> getCoursesByStudent(Long userId) {
        List<CourseDTO> courses = enrollmentService.getCoursesByStudent(userId);

        // 🔧 Ensure instructor is always included
        for (CourseDTO course : courses) {
            if (course.getInstructor() == null && course.getCourseId() != null) {
                CourseDTO full = courseService.getCourseById(course.getCourseId());
                course.setInstructor(full.getInstructor());
            }
        }
        return courses;
    }

    public List<UserDTO> getStudentsByCourse(Long courseId) {
        return enrollmentService.getStudentsByCourse(courseId);
    }
}