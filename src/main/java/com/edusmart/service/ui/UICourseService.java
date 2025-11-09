package com.edusmart.service.ui;

import com.edusmart.dto.CourseDTO;
import com.edusmart.service.CourseService;
import com.edusmart.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UICourseService {
    @Autowired
    private CourseService courseService;

    @Autowired
    private EnrollmentService enrollmentService;

    public List<CourseDTO> getAllCourses(){
        return courseService.getAllCourse();
    }

    public CourseDTO getCourseById(Long id){
        return courseService.getCourseById(id);
    }

    public void enrollUser(Long userId,Long courseId){
        enrollmentService.enrollStudent(userId,courseId);
    }

}
