package com.edusmart.service;

import com.edusmart.dto.CreateNotificationDTO;
import com.edusmart.dto.NotificationDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NotificationService {
    NotificationDTO createNotification(CreateNotificationDTO notificationDTO);
    List<NotificationDTO> getNotificationForUser(Long userId);
    NotificationDTO markAsRead(Long notificationId);
    void markAllAsRead(Long userId);
    void deleteNotification(Long notificationId);
}
