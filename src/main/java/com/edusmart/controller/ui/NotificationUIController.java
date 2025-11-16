package com.edusmart.controller.ui;

import com.edusmart.dto.NotificationDTO;
import com.edusmart.entity.Users;
import com.edusmart.repository.UserRepository;
import com.edusmart.service.ui.UINotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;

@Controller
public class NotificationUIController {

    @Autowired
    private UINotificationService uiNotificationService;

    @Autowired
    private UserRepository userRepository;

    // Helper method to extract logged-in userId
    private Long getLoggedInUserId(Principal principal) {
        String email = principal.getName(); // Email = username
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));
        return user.getUserId();
    }

    // Full notifications page
    @GetMapping("/notifications")
    public String viewNotifications(Model model, Principal principal) {

        Long userId = getLoggedInUserId(principal);

        List<NotificationDTO> notifications = uiNotificationService.getNotificationsForUser(userId);

        model.addAttribute("notifications", notifications);
        model.addAttribute("userId", userId);

        return "notifications";
    }

    // Mark a single notification as read
    @GetMapping("/notifications/read/{id}")
    public String markAsRead(@PathVariable Long id, Principal principal) {

        Long userId = getLoggedInUserId(principal);

        uiNotificationService.markAsRead(id, userId);

        return "redirect:/notifications";
    }

    // Mark all as read
    @GetMapping("/notifications/read-all")
    public String markAllAsRead(Principal principal) {

        Long userId = getLoggedInUserId(principal);

        uiNotificationService.markAllAsRead(userId);

        return "redirect:/notifications";
    }
}