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
    public NotificationService notificationService;

    @MessageMapping("/notify")
    public void processNotification(CreateNotificationDTO notificationDTO){
        NotificationDTO savedNotification=notificationService.createNotification(notificationDTO);
        messagingTemplate.convertAndSendToUser(
                savedNotification.getUserId().toString(),
                "/queue/notification",
                savedNotification
        );
    }

    @MessageMapping("/broadcast")
    public void broadcastNotification(CreateNotificationDTO notificationDTO){
        NotificationDTO saved=notificationService.createNotification(notificationDTO);

        messagingTemplate.convertAndSend("/topic/global",saved);
    }

    @MessageMapping("/ack")
    public void acknowledgeNotification(NotificationDTO notificationDTO){
        notificationService.markAsRead(notificationDTO.getNotificationId());

        messagingTemplate.convertAndSendToUser(
                notificationDTO.getUserId().toString()
                ,"/queue/acknowledgments",
                "NotificationId "+notificationDTO.getNotificationId() +" marked as read."
        );
    }

    private void sendToUser(Long userId,NotificationDTO dto){
        messagingTemplate.convertAndSendToUser(
                userId.toString(),
                "/queue/notifications",
                dto
        );
    }
}
