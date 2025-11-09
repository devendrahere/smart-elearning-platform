package com.edusmart.controller.ui;

import com.edusmart.service.UserService;
import com.edusmart.service.ui.UIEnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserUIController {

    @Autowired
    private UserService userService;

    @Autowired
    private UIEnrollmentService enrollmentService;

    @GetMapping("/profile")
    public String showProfile(Authentication authentication, Model model) {
        String username = authentication.getName();
        var user = userService.getUserByUsername(username);
        model.addAttribute("user", user);
        return "my-profile";
    }

    @GetMapping("/my-courses")
    public String showMyCourses(Authentication authentication, Model model) {
        String username = authentication.getName();
        var user = userService.getUserByUsername(username);

        // Get all enrolled courses
//        var courses = enrollmentService.getCoursesByStudent(user.getUserId());

        model.addAttribute("courses", enrollmentService.getCoursesByStudent(user.getUserId()));
        return "my-courses";
    }
}
