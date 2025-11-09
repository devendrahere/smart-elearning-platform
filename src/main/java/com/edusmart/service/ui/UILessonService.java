package com.edusmart.service.ui;

import com.edusmart.dto.LessonDTO;
import com.edusmart.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UILessonService {

    @Autowired
    private LessonService lessonService;

    public List<LessonDTO> getLessonsByCourse(Long courseId){
        return lessonService.getLessonByCourse(courseId);
    }

    public LessonDTO getLessonByID(Long id){
        return lessonService.getLessonById(id);
    }

}
