package com.edusmart.controller.ui;

import com.edusmart.dto.CourseDTO;
import com.edusmart.dto.LessonDTO;
import com.edusmart.dto.QuizDTO;
import com.edusmart.service.QuizService;
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

import java.util.List;

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

    @Autowired
    private QuizService quizService;

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
        boolean isInstructor = false;

        // Always fetch the course once
        var course = courseService.getCourseById(courseId);
        model.addAttribute("course", course);

        // Fetch lessons and announcements (public data)
        model.addAttribute("lessons", lessonService.getLessonsByCourse(courseId));
        model.addAttribute("announcements", announcementService.getAnnouncementsForCourse(courseId));

        boolean enrolled = false;

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            var user = userService.getUserByUsername(username);
            userId = user.getUserId();

            // ✅ Check if user is instructor for this course
            if (course.getInstructor() != null &&
                    course.getInstructor().getUserId().equals(userId)) {
                isInstructor = true;
            }

            // Enrollment check for students
            enrolled = enrollmentService.isStudentEnrolled(userId, courseId);
            model.addAttribute("enrolled", enrolled);

            // ✅ Fetch quiz result for this course (if any)
            try {
                var allResults = quizService.getResultByUser(userId);
                var lastResult = allResults.stream()
                        .filter(r -> {
                            var quiz = quizService.getQuizById(r.getQuizId());
                            return quiz.getCourseId().equals(courseId);
                        })
                        .findFirst()
                        .orElse(null);

                model.addAttribute("lastResult", lastResult);
            } catch (Exception e) {
                model.addAttribute("lastResult", null);
            }
        }

        // ✅ Only instructor sees enrolled students
        if (isInstructor) {
            model.addAttribute("students", enrollmentService.getStudentsByCourse(courseId));
        }

        model.addAttribute("isInstructor", isInstructor);
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

    // -------------------------
    // 4️⃣ Manage lessons (Instructor-only)
    // -------------------------
    @GetMapping("/{courseId}/lessons/manage")
    public String manageLessons(@PathVariable Long courseId,
                                Authentication authentication,
                                Model model) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        String username = authentication.getName();
        Long instructorId = userService.getUserByUsername(username).getUserId();

        // Fetch course and verify ownership
        CourseDTO course = courseService.getCourseById(courseId);
        if (course.getInstructor() == null ||
                !course.getInstructor().getUserId().equals(instructorId)) {
            return "error/403"; // Not the owner
        }

        model.addAttribute("course", course);
        model.addAttribute("lessons", lessonService.getLessonsByCourse(courseId));

        // ✅ Fetch quiz (if any) to show "Add/Edit Quiz" in manage-lessons
        QuizDTO quiz = null;
        try {
            quiz = quizService.getAllQuizzes().stream()
                    .filter(q -> q.getCourseId().equals(courseId))
                    .findFirst()
                    .orElse(null);
        } catch (Exception ignored) {}

        model.addAttribute("quiz", quiz);

        return "manage-lessons";
    }

    // -------------------------
    // 5️⃣ Show lessons (Student + Instructor)
    // -------------------------
    @GetMapping("/{courseId}/lessons")
    public String showLessons(@PathVariable Long courseId,
                              Authentication authentication,
                              Model model) {

        CourseDTO course = courseService.getCourseById(courseId);
        List<LessonDTO> lessons = lessonService.getLessonsByCourse(courseId);

        boolean isInstructor = false;
        boolean enrolled = false;

        if (authentication != null && authentication.isAuthenticated()) {
            var user = userService.getUserByUsername(authentication.getName());
            if (course.getInstructor() != null &&
                    course.getInstructor().getUserId().equals(user.getUserId())) {
                isInstructor = true;
            }
            enrolled = enrollmentService.isStudentEnrolled(user.getUserId(), courseId);
        }

        // Restrict access: only instructor or enrolled students
        if (!isInstructor && !enrolled) {
            return "redirect:/courses/" + courseId;
        }

        // ✅ Fetch quiz for this course
        QuizDTO quiz = null;
        try {
            quiz = quizService.getAllQuizzes().stream()
                    .filter(q -> q.getCourseId().equals(courseId))
                    .findFirst()
                    .orElse(null);
        } catch (Exception ignored) {}

        model.addAttribute("course", course);
        model.addAttribute("lessons", lessons);
        model.addAttribute("isInstructor", isInstructor);
        model.addAttribute("quiz", quiz);

        return "lesson-list";
    }
}