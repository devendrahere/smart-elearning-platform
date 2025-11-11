package com.edusmart.service;

import com.edusmart.dto.LessonDTO;
import com.edusmart.entity.Courses;

import java.util.List;

public interface LessonService {
    LessonDTO createLesson(Long courseId,LessonDTO lessonDTO);
    LessonDTO getLessonById(Long lessonId);
    List<LessonDTO> getLessonByCourse(Long courseId);
    LessonDTO updateLessonById(Long lessonId,LessonDTO lessonDTO);
    void deleteLesson(Long lessonId);
    Courses getCourseByLessonId(Long lessonId);
}
