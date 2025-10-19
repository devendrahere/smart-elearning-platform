package com.edusmart.repository;

import com.edusmart.dto.NotificationDTO;
import com.edusmart.entity.Notification;
import com.edusmart.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {
    List<Notification> findByUserUserId(Long userId);
}
