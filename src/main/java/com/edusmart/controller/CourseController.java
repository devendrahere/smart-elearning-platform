package com.edusmart.controller;

import com.edusmart.dto.CourseDTO;
import com.edusmart.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    //creating a new course
    //body CourseDTO
    //url /api/courses/
    @PostMapping("/")
    public ResponseEntity<CourseDTO> createCourse(@RequestBody CourseDTO courseDTO){
        CourseDTO course= courseService.createCourse(courseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(course);
    }

    //fetching the course by id
    //ID is in url path so using path variable annotation
    //url /api/courses/{courseId}
    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable Long courseId){
        CourseDTO course= courseService.getCourseById(courseId);
        return ResponseEntity.ok(course);
    }

    //updating the course
    //PathVariable courseId and RequestBody CourseDTO
    //url /api/courses/{courseId}
    @PutMapping("/{courseId}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable Long courseId,@RequestBody CourseDTO courseDTO){
        CourseDTO updatedCourse= courseService.updateCourse(courseId,courseDTO);
        return ResponseEntity.ok(updatedCourse);
    }

    //fetching all courses
    //url /api/courses/courseList
    @GetMapping("/courseList")
    public  ResponseEntity<List<CourseDTO>> getAllCourse(){
        return ResponseEntity.ok(courseService.getAllCourse());
    }

    //deleting the course by id
    //url /api/courses/{courseId}
    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long courseId){
        courseService.deleteCourse(courseId);
        return ResponseEntity.noContent().build();
    }
}
