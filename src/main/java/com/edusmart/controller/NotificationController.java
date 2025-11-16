package com.edusmart.controller;

import com.edusmart.dto.CreateNotificationDTO;
import com.edusmart.dto.NotificationDTO;
import com.edusmart.dto.UserDTO;
import com.edusmart.service.NotificationService;
import com.edusmart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    // Create notification
    @PostMapping("/")
    public ResponseEntity<NotificationDTO> createNotification(@RequestBody CreateNotificationDTO notification){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                notificationService.createNotification(notification)
        );
    }

    // Get notifications for user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationDTO>> getNotificationForUser(@PathVariable Long userId){
        return ResponseEntity.ok(notificationService.getNotificationForUser(userId));
    }

    // Mark single notification as read (SECURE)
    @PutMapping("/{id}/read")
    public ResponseEntity<?> markAsRead(
            @PathVariable Long id,
            Principal principal) {

        // principal.getName() returns email (username)
        UserDTO loggedInUser = userService.getUserByUsername(principal.getName());

        Long userId = loggedInUser.getUserId();

        NotificationDTO dto = notificationService.markAsRead(id, userId);

        return ResponseEntity.ok(dto);
    }

    // Mark all notifications as read
    @PutMapping("/user/{userId}/read-all")
    public ResponseEntity<Void> markAllAsRead(@PathVariable Long userId) {
        notificationService.markAllAsRead(userId);
        return ResponseEntity.noContent().build();
    }

    // Delete notification
    @DeleteMapping("/{notificationId}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long notificationId){
        notificationService.deleteNotification(notificationId);
        return ResponseEntity.noContent().build();
    }
}