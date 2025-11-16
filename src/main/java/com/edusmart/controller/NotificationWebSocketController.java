package com.edusmart.controller;

import com.edusmart.dto.CreateNotificationDTO;
import com.edusmart.dto.NotificationDTO;
import com.edusmart.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationWebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private NotificationService notificationService;

    // Create + push notification to specific user
    @MessageMapping("/notify")
    public void processNotification(CreateNotificationDTO notificationDTO){
        NotificationDTO saved = notificationService.createNotification(notificationDTO);

        messagingTemplate.convertAndSendToUser(
                saved.getUserId().toString(),
                "/queue/notifications",     // FIXED: must match service
                saved
        );
    }

    // Create + push to global topic
    @MessageMapping("/broadcast")
    public void broadcastNotification(CreateNotificationDTO notificationDTO){
        NotificationDTO saved = notificationService.createNotification(notificationDTO);

        messagingTemplate.convertAndSend(
                "/topic/global",
                saved
        );
    }

    // Mark notification as read
    @MessageMapping("/ack")
    public void acknowledgeNotification(NotificationDTO notificationDTO){

        notificationService.markAsRead(
                notificationDTO.getNotificationId(),
                notificationDTO.getUserId()       // use the userId from DTO
        );

        messagingTemplate.convertAndSendToUser(
                notificationDTO.getUserId().toString(),
                "/queue/acknowledgments",
                "Notification " + notificationDTO.getNotificationId() + " marked as read"
        );
    }
}