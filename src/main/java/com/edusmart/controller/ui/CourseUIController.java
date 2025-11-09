package com.edusmart.controller.ui;

import com.edusmart.service.UserService;
import com.edusmart.service.ui.UIAnnouncementService;
import com.edusmart.service.ui.UICourseService;
import com.edusmart.service.ui.UIEnrollmentService;
import com.edusmart.service.ui.UILessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/courses")
public class CourseUIController {

    @Autowired
    private UICourseService courseService;

    @Autowired
    private UILessonService lessonService;

    @Autowired
    private UIAnnouncementService announcementService;

    @Autowired
    private UIEnrollmentService enrollmentService;

    @Autowired
    private UserService userService;

    // -------------------------
    // 1️⃣ Show all courses
    // -------------------------
    @GetMapping
    public String showCourses(Authentication authentication, Model model) {
        Long userId = null;

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            userId = userService.getUserByUsername(username).getUserId();
        }

        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("userId", userId); // can be null
        return "courses";
    }

    // -------------------------
    // 2️⃣ Show course details
    // -------------------------
    @GetMapping("/{courseId}")
    public String courseDetails(@PathVariable Long courseId,
                                Authentication authentication,
                                Model model) {
        Long userId = null;

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            userId = userService.getUserByUsername(username).getUserId();
        }

        model.addAttribute("course", courseService.getCourseById(courseId));
        model.addAttribute("lessons", lessonService.getLessonsByCourse(courseId));
        model.addAttribute("announcements", announcementService.getAnnouncementsForCourse(courseId));
        model.addAttribute("students", enrollmentService.getStudentsByCourse(courseId));

        boolean enrolled = false;
        if (userId != null) {
            enrolled = enrollmentService.isStudentEnrolled(userId, courseId);
        }

        model.addAttribute("enrolled", enrolled);
        model.addAttribute("userId", userId);

        return "course-details";
    }

    // -------------------------
    // 3️⃣ Enroll student in course
    // -------------------------
    @PostMapping("/{courseId}/enroll")
    public String enrollCourse(@PathVariable Long courseId, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            // Redirect non-logged users to login
            return "redirect:/login";
        }

        String username = authentication.getName();
        Long userId = userService.getUserByUsername(username).getUserId();

        enrollmentService.enrollStudent(userId, courseId);

        return "redirect:/courses/" + courseId;
    }
}
