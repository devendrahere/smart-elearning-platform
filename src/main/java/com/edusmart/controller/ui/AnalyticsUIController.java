package com.edusmart.controller.ui;

import com.edusmart.dto.CourseDTO;
import com.edusmart.dto.PlatformOverviewDTO;
import com.edusmart.service.AnalyticsLogService;
import com.edusmart.service.UserService;
import com.edusmart.service.ui.UICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/analytics")
public class AnalyticsUIController {

    @Autowired
    private AnalyticsLogService analyticsLogService;

    @Autowired
    private UICourseService uiCourseService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String showInstructorAnalytics(Authentication authentication, Model model) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        String username = authentication.getName();
        Long instructorId = userService.getUserByUsername(username).getUserId();

        // Fetch all courses taught by this instructor
        List<CourseDTO> instructorCourses = uiCourseService.getCoursesByInstructor(instructorId);
        model.addAttribute("courses", instructorCourses);

        // Add flag for Thymeleaf condition rendering
        model.addAttribute("isInstructor", true);

        return "analytics"; // Thymeleaf view (analytics.html)
    }

    @GetMapping("/course/{courseId}")
    public String showCourseOverview(@PathVariable Long courseId, Model model) {
        PlatformOverviewDTO overview = analyticsLogService.getCourseEngagement(courseId);
        model.addAttribute("overview", overview);
        return "course-overview"; // Create this Thymeleaf page
    }
    @GetMapping("/platform")
    public String showPlatformOverview(Model model) {
        PlatformOverviewDTO overview = analyticsLogService.getPlatformOverview();
        model.addAttribute("overview", overview);
        return "platform-overview"; // this will map to templates/platform-overview.html
    }
}
