package com.edusmart.service.implemeted;

import com.edusmart.dto.CreateNotificationDTO;
import com.edusmart.dto.NotificationDTO;
import com.edusmart.entity.Notification;
import com.edusmart.entity.Users;
import com.edusmart.repository.NotificationRepository;
import com.edusmart.repository.UserRepository;
import com.edusmart.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImple implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;



    @Override
    public NotificationDTO createNotification(CreateNotificationDTO notificationDTO) {
        Users user= userRepository.findById(notificationDTO.getUserId())
                .orElseThrow(()->new RuntimeException("User not found by id: "+notificationDTO.getUserId()));

        Notification notification= new Notification();
        notification.setCreatedAt(LocalDateTime.now());
        notification.setUser(user);
        notification.setMessage(notificationDTO.getMessage());
        notification.setRead(false);
        notification.setTitle(notificationDTO.getType());
        notification.setTitle(notificationDTO.getTitle());

        Notification saved= notificationRepository.save(notification);

        return mapToDTO(saved);
    }

    @Override
    public List<NotificationDTO> getNotificationForUser(Long userId) {
        return notificationRepository.findByUserUserId(userId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public NotificationDTO markAsRead(Long notificationId) {
        Notification notification= notificationRepository.findById(notificationId)
                .orElseThrow(()->new RuntimeException("Notification not found with id: "+notificationId));
        notification.setRead(true);
        Notification updated=notificationRepository.save(notification);
        return mapToDTO(updated);
    }

    @Override
    public void markAllAsRead(Long userId) {
        List<Notification> notifications=notificationRepository.findByUserUserId(userId);

        notifications.forEach(n->n.setRead(true));
        notificationRepository.saveAll(notifications);
    }

    @Override
    public void deleteNotification(Long notificationId) {
        if(!notificationRepository.existsById(notificationId)){
            throw  new RuntimeException("No notification found with id: "+notificationId);
        }
        notificationRepository.deleteById(notificationId);
    }

    //mapping helper

    private NotificationDTO mapToDTO(Notification entity){
        NotificationDTO dto=new NotificationDTO();
        dto.setNotificationId(entity.getNotificationId());
        dto.setMessage(entity.getMessage());
        dto.setRead(entity.getRead());
        dto.setType(entity.getType());
        dto.setTitle(entity.getTitle());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUserId(entity.getUser().getUserId());

        return dto;
    }
}
