package com.edusmart.controller.ui;

import com.edusmart.dto.CourseDTO;
import com.edusmart.dto.LessonDTO;
import com.edusmart.dto.UserDTO;
import com.edusmart.entity.Courses;
import com.edusmart.entity.Lessons;
import com.edusmart.entity.Users;
import com.edusmart.service.UserService;
import com.edusmart.service.ui.UICourseService;
import com.edusmart.service.ui.UILessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/lesson")
public class LessonUIController {

    @Autowired private UILessonService lessonService;
    @Autowired private UICourseService courseService;
    @Autowired private UserService userService;

    // -------------------------
    // 1️⃣ View Lesson (anyone)
    // -------------------------
    @GetMapping("/{lessonId}")
    public String viewLesson(@PathVariable Long lessonId,
                             Authentication authentication,
                             Model model) {
        LessonDTO lesson = lessonService.getLessonByID(lessonId);
        Courses course = lessonService.getCourseByLessonId(lessonId);

        boolean isInstructor = false;
        if (authentication != null && authentication.isAuthenticated()) {
            UserDTO user = userService.getUserByUsername(authentication.getName());
            if (course.getInstructor().getUserId().equals(user.getUserId())) {
                isInstructor = true;
            }
        }

        // ✅ Navigation: previous and next lessons
        List<LessonDTO> allLessons = lessonService.getLessonsByCourse(course.getCourseId());
        allLessons.sort((a, b) -> Integer.compare(a.getLessonOrder(), b.getLessonOrder()));

        Long prevLessonId = null;
        Long nextLessonId = null;

        for (int i = 0; i < allLessons.size(); i++) {
            if (allLessons.get(i).getLessonId().equals(lessonId)) {
                if (i > 0) prevLessonId = allLessons.get(i - 1).getLessonId();
                if (i < allLessons.size() - 1) nextLessonId = allLessons.get(i + 1).getLessonId();
                break;
            }
        }

        model.addAttribute("lesson", lesson);
        model.addAttribute("isInstructor", isInstructor);
        model.addAttribute("prevLessonId", prevLessonId);
        model.addAttribute("nextLessonId", nextLessonId);

        return "lesson-view"; // ← Use this page for enrolled students
    }

    // -------------------------
    // 2️⃣ Show Add Lesson Form (Instructor only)
    // -------------------------
    @GetMapping("/add/{courseId}")
    public String showAddLessonForm(@PathVariable Long courseId,
                                    Authentication authentication,
                                    Model model) {

        // 🔒 Step 1: Check authentication and instructor ownership
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        UserDTO user = userService.getUserByUsername(authentication.getName());
        CourseDTO course = courseService.getCourseById(courseId);

        if (course == null || course.getInstructor() == null ||
                !course.getInstructor().getUserId().equals(user.getUserId())) {
            return "error/403"; // Not the instructor for this course
        }

        // ✅ Step 2: Prepare lesson form model
        LessonDTO newLesson = new LessonDTO();
        newLesson.setCourseId(courseId);

        model.addAttribute("course", course);
        model.addAttribute("lesson", newLesson);
        model.addAttribute("courseId", courseId);
        model.addAttribute("isInstructor", true);

        // ✅ Step 3: Return to the correct form view
        return "lesson-form";
    }


    // -------------------------
    // 3️⃣ Add Lesson (Instructor only)
    // -------------------------
    @PostMapping("/add/{courseId}")
    public String addLesson(@PathVariable Long courseId,
                            @ModelAttribute LessonDTO lessonDTO,
                            Authentication authentication) {

        if (!isInstructorOfCourse(courseId, authentication)) {
            return "redirect:/access-denied";
        }

        lessonService.createLesson(courseId, lessonDTO);
        return "redirect:/courses/" + courseId;
    }

    @PostMapping("/delete/{lessonId}")
    public String deleteLesson(@PathVariable Long lessonId, Authentication authentication) {
        Courses course = lessonService.getCourseByLessonId(lessonId);

        if (!isInstructorOfCourse(course.getCourseId(), authentication)) {
            return "redirect:/access-denied";
        }

        lessonService.deleteLesson(lessonId);
        return "redirect:/courses/" + course.getCourseId() + "/lessons/manage";
    }

    @GetMapping("/edit/{lessonId}")
    public String showEditLessonForm(@PathVariable Long lessonId, Authentication authentication, Model model) {
        LessonDTO lesson = lessonService.getLessonByID(lessonId);
        Courses course = lessonService.getCourseByLessonId(lessonId);

        if (!isInstructorOfCourse(course.getCourseId(), authentication)) {
            return "error/403";
        }

        model.addAttribute("lesson", lesson);
        model.addAttribute("courseId", course.getCourseId());
        return "lesson-form";
    }

    @PostMapping("/edit/{lessonId}")
    public String updateLesson(@PathVariable Long lessonId,
                               @ModelAttribute LessonDTO lessonDTO,
                               Authentication authentication) {
        Courses course = lessonService.getCourseByLessonId(lessonId);

        if (!isInstructorOfCourse(course.getCourseId(), authentication)) {
            return "redirect:/access-denied";
        }

        lessonService.updateLesson(lessonId, lessonDTO);
        return "redirect:/courses/" + course.getCourseId() + "/lessons/manage";
    }

    // -------------------------
    // 🔒 Ownership Check
    // -------------------------
    private boolean isInstructorOfCourse(Long courseId, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        var user = userService.getUserByUsername(authentication.getName());
        var course = courseService.getCourseById(courseId);
        return course.getInstructor().getUserId().equals(user.getUserId());
    }
}
