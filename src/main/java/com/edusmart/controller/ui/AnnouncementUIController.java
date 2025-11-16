package com.edusmart.controller.ui;

import com.edusmart.dto.AnnouncementDTO;
import com.edusmart.service.UserService;
import com.edusmart.service.ui.UIAnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/courses/{courseId}/announcements")
public class AnnouncementUIController {

    @Autowired
    private UIAnnouncementService announcementService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String showAnnouncements(@PathVariable Long courseId,
                                    Authentication authentication,
                                    Model model) {

        Long loggedInId = null;
        boolean isInstructor = false;

        if (authentication != null && authentication.isAuthenticated()) {
            var user = userService.getUserByUsername(authentication.getName());
            loggedInId = user.getUserId();

            // check instructor
            isInstructor = announcementService.isInstructorOfCourse(courseId, loggedInId);
        }

        List<AnnouncementDTO> announcements = announcementService.getAnnouncementsForCourse(courseId);

        model.addAttribute("announcements", announcements);
        model.addAttribute("courseId", courseId);
        model.addAttribute("loggedInId", loggedInId);
        model.addAttribute("isInstructor", isInstructor);

        return "announcements";
    }

    @PostMapping("/create")
    public String createAnnouncement(@PathVariable Long courseId,
                                     @RequestParam String message,
                                     Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        var user = userService.getUserByUsername(authentication.getName());
        Long loggedInId = user.getUserId();

        // only instructor allowed
        if (!announcementService.isInstructorOfCourse(courseId, loggedInId)) {
            return "error/403";
        }

        announcementService.createAnnouncement(courseId, loggedInId, message);
        return "redirect:/courses/" + courseId + "/announcements";
    }

    @PostMapping("/{announcementId}/delete")
    public String deleteAnnouncement(@PathVariable Long courseId,
                                     @PathVariable Long announcementId,
                                     Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        var user = userService.getUserByUsername(authentication.getName());
        Long loggedInId = user.getUserId();

        announcementService.deleteAnnouncement(courseId, announcementId, loggedInId);

        return "redirect:/courses/" + courseId + "/announcements";
    }
}