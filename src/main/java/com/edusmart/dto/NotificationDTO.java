package com.edusmart.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class NotificationDTO {
    private Long notificationId;
    @NotNull(message = "User Id is required")
    private Long userId;

    @NotBlank(message = "Title is required")
    @Size(min = 3,max = 100,message = "The Title length must be in 3 and 100")
    private String title;
    @NotBlank(message = "Message is required")
    @Size(max = 200,message = "The Message length should be in 200")
    private String message;
    @NotBlank(message = "Notification Type is required")
    private String type;
    @NotNull(message = "Read status is required")
    private boolean read;
    private LocalDateTime createdAt;

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
