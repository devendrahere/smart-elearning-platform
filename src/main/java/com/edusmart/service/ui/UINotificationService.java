package com.edusmart.service.ui;

import com.edusmart.dto.NotificationDTO;
import com.edusmart.service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UINotificationService {

    private final NotificationService notificationService;

    public UINotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public List<NotificationDTO> getNotificationsForUser(Long userId) {
        return notificationService.getNotificationForUser(userId);
    }

    public void markAsRead(Long notificationId, Long userId) {
        notificationService.markAsRead(notificationId, userId);
    }

    public void markAllAsRead(Long userId) {
        notificationService.markAllAsRead(userId);
    }
}
