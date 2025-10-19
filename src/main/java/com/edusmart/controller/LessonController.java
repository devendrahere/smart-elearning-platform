package com.edusmart.controller;

import com.edusmart.dto.LessonDTO;
import com.edusmart.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    @Autowired
    private LessonService lessonService;

    //creating lesson using lesson dto and course id
    //url /api/lessons/course/{courseId}
    //body LessonDTO
    @PostMapping("/course/{courseId}")
    public ResponseEntity<LessonDTO> createLesson(@PathVariable Long courseId ,@RequestBody LessonDTO lesson){
        return ResponseEntity.status(HttpStatus.CREATED).body(lessonService.createLesson(courseId,lesson));
    }

    //fetching the lesson by lesson id
    //url /api/lessons/{lessonId}
    //body LessonDTO
    @GetMapping("{lessonId}")
    public ResponseEntity<LessonDTO> getLessonById(@PathVariable Long lessonId){
        return ResponseEntity.ok(lessonService.getLessonById(lessonId));
    }

    //fetching all lessons under one course
    //url /api/lessons/course/{courseId}
    //body LessonDTO
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<LessonDTO>> getLessonsByCourse(@PathVariable Long courseId){
        return ResponseEntity.ok(lessonService.getLessonByCourse(courseId));
    }

    //updating the lesson by lesson id
    //url /api/lessons/{lessonId}
    //body LessonDTO
    @PutMapping("/{lessonId}")
    public ResponseEntity<LessonDTO> updateLesson(@PathVariable Long lessonId , @RequestBody LessonDTO updatedLesson){
        return ResponseEntity.ok(lessonService.updateLessonById(lessonId,updatedLesson));
    }

    //deleting the lesson by lesson id
    //url /api/lessons/{lessonId}
    @DeleteMapping("/{lessonId}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long lessonId){
        lessonService.deleteLesson(lessonId);
        return ResponseEntity.noContent().build();
    }
}
