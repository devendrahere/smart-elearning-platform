package com.edusmart.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateNotificationDTO {
    private Long userId;
    @NotBlank(message = "Title cannot be blank")
    @Size(min = 3,max = 100,message = "The title should be in 3 and 100 length")
    private String title;
    @Size(max = 200,message = "Message length should under 200")
    private String message;
    @NotBlank(message = "Notification cannot be blank !")
    private String type;

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
}