package com.edusmart.controller;

import com.edusmart.dto.CourseDTO;
import com.edusmart.dto.EnrollmentsDTO;
import com.edusmart.dto.UserDTO;
import com.edusmart.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    //enrolling a student to course
    //right now we have 2 id that need to be fetched so we use request param annotation
    //url /api/enrollments/enroll
    @PostMapping("/enroll")
    public ResponseEntity<EnrollmentsDTO> enrollStudent(@RequestParam Long userId,@RequestParam Long courseId){
        return ResponseEntity.status(HttpStatus.CREATED).body(enrollmentService.enrollStudent(userId,courseId));
    }

    //check if the student is enrolled or not to a course
    //for this we also need to take parameter input
    // url /api/enrollments/
    @GetMapping("/")
    public ResponseEntity<Boolean> isStudentEnrolled(@RequestParam Long userId,@RequestParam Long courseId){
        return ResponseEntity.ok(enrollmentService.isStudentEnrolled(userId,courseId));
    }

    //unenrolling a student from course using course id and user id
    //url /api/enrollments/
    @DeleteMapping("/")
    public ResponseEntity<Void> unenrollStudent(@RequestParam Long userId,@RequestParam Long courseId){
        enrollmentService.unenrollStudent(userId,courseId);
        return ResponseEntity.noContent().build();
    }

    //getting all course by user id
    //url /api/enrollments/courseOfStudent/{userId}
    @GetMapping("courseOfStudent/{userId}")
    public ResponseEntity<List<CourseDTO>> getCourseByStudent(@PathVariable Long userId){
        return ResponseEntity.ok(enrollmentService.getCoursesByStudent(userId));
    }

    //this is to fetch all students enrolled for 1 single course using course id
    // url /api/enrollments/studentsCourse/{courseId}
    @GetMapping("/studentsCourse/{courseId}")
    public ResponseEntity<List<UserDTO>> getStudentsByCourse(@PathVariable Long courseId){
        return ResponseEntity.ok(enrollmentService.getStudentsByCourse(courseId));
    }
}
