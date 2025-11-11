package com.edusmart.service.ui;

import com.edusmart.dto.LessonDTO;
import com.edusmart.entity.Courses;
import com.edusmart.service.CourseService;
import com.edusmart.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UILessonService {

    @Autowired
    private LessonService lessonService;

    @Autowired
    private CourseService courseService;

    // Get all lessons for a course
    public List<LessonDTO> getLessonsByCourse(Long courseId){
        return lessonService.getLessonByCourse(courseId);
    }

    // Get a specific lesson
    public LessonDTO getLessonByID(Long id){
        return lessonService.getLessonById(id);
    }

    // ✅ Add lesson to course
    public LessonDTO createLesson(Long courseId, LessonDTO lessonDTO){
        return lessonService.createLesson(courseId, lessonDTO);
    }

    // ✅ Update lesson
    public LessonDTO updateLesson(Long lessonId, LessonDTO lessonDTO){
        return lessonService.updateLessonById(lessonId, lessonDTO);
    }

    // ✅ Delete lesson
    public void deleteLesson(Long lessonId){
        lessonService.deleteLesson(lessonId);
    }

    public Courses getCourseByLessonId(Long lessonId) {
        return lessonService.getCourseByLessonId(lessonId);
    }
}