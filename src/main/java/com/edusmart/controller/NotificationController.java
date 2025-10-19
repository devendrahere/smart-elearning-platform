package com.edusmart.controller;

import com.edusmart.dto.CreateNotificationDTO;
import com.edusmart.dto.NotificationDTO;
import com.edusmart.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    //creating notification
    //url /api/notification/
    @PostMapping("/")
    public ResponseEntity<NotificationDTO> createNotification(@RequestBody CreateNotificationDTO notification){
        return ResponseEntity.status(HttpStatus.CREATED).body(notificationService.createNotification(notification));
    }

    //fetching notification for user with userId
    //url /api/notification/user/{userId}
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationDTO>> getNotificationForUser(@PathVariable Long userId){
        return ResponseEntity.ok(notificationService.getNotificationForUser(userId));
    }

    //updating boolean value of read
    //url /api/notification/{notificationId}
    @PutMapping("/{notificationId}")
    public ResponseEntity<NotificationDTO> markAsRead(@PathVariable Long notificationId){
        return ResponseEntity.ok(notificationService.markAsRead(notificationId));
    }
    //updating all notification to read
    //url /api/notification/user/{userId}
    @PutMapping("/user/{userId}")
    public ResponseEntity<Void> markAllAsRead(@PathVariable Long userId){
        notificationService.markAllAsRead(userId);
        return ResponseEntity.noContent().build();
    }

    //deleting notification using notification id
    //url /api/notification/{notificationId}
    @DeleteMapping("/{notificationId}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long notificationId){
        notificationService.deleteNotification(notificationId);
        return ResponseEntity.noContent().build();
    }
}
