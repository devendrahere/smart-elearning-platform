package com.edusmart.controller.ui;

import com.edusmart.service.ui.UILessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/lesson")
public class LessonUIController {
    @Autowired private UILessonService lessonService;

    @GetMapping("{lessonId}")
    public String viewLesson(@PathVariable Long lessonId, Model model){
        model.addAttribute("lesson",lessonService.getLessonByID(lessonId));
        return "lesson";
    }
}