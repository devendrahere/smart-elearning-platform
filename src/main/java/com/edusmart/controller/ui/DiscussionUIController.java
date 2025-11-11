package com.edusmart.controller.ui;

import com.edusmart.dto.CourseDTO;
import com.edusmart.dto.DiscussionDTO;
import com.edusmart.dto.DiscussionThreadDTO;
import com.edusmart.dto.UserDTO;
import com.edusmart.service.DiscussionService;
import com.edusmart.service.UserService;
import com.edusmart.service.ui.UICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/discussion")
public class DiscussionUIController {

    @Autowired
    private DiscussionService discussionService;

    @Autowired
    private UICourseService courseService;

    @Autowired
    private UserService userService;

    //  Show all discussion threads for a course
    @GetMapping("/course/{courseId}")
    public String showCourseDiscussions(@PathVariable Long courseId,
                                        Authentication authentication,
                                        Model model) {
        CourseDTO course = courseService.getCourseById(courseId);
        List<DiscussionThreadDTO> threads = discussionService.getThreadByCourse(courseId);

        Long userId = null;
        if (authentication != null && authentication.isAuthenticated()) {
            userId = userService.getUserByUsername(authentication.getName()).getUserId();
        }

        model.addAttribute("course", course);
        model.addAttribute("threads", threads);
        model.addAttribute("userId", userId);

        return "discussion-list";
    }

    @GetMapping("/thread/{threadId}")
    public String showThread(@PathVariable Long threadId,
                             Authentication authentication,
                             Model model) {
        DiscussionThreadDTO thread = discussionService.getThreadById(threadId);

        if (thread == null) {
            return "redirect:/discussion/course"; // You can append courseId if available
        }

        List<DiscussionDTO> messages = discussionService.getMessageByThread(threadId);
        Long userId = null;
        String username = null;
        boolean isInstructor = false;

        if (authentication != null && authentication.isAuthenticated()) {
            var user = userService.getUserByUsername(authentication.getName());
            userId = user.getUserId();
            username = user.getUsername();

            var course = courseService.getCourseById(thread.getCourseId());
            if (course != null && course.getInstructor() != null &&
                    course.getInstructor().getUserId().equals(userId)) {
                isInstructor = true;
            }
        }

        model.addAttribute("thread", thread);
        model.addAttribute("messages", messages);
        model.addAttribute("userId", userId);
        model.addAttribute("username", username);
        model.addAttribute("isInstructor", isInstructor);
        model.addAttribute("isThreadOwner", thread.getCreatedBy().equals(userId));

        return "discussion-thread";
    }
}