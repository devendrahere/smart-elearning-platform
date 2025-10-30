package com.edusmart.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class AnalyticsLogDTO {
    private Long analyticLogId;
    @NotNull(message = "User Id is required")
    private Long userId;
    @NotNull(message = "CourseId is required")
    private Long courseId;
    @NotBlank(message = "Event Type is required")
    private String eventType;
    @NotBlank(message = "Course Title is required")
    @Size(min=3,max = 100,message = "Course Title Length must be in 3 and 100")
    private String courseTitle;

    private LocalDateTime eventTimestamp;

    @NotBlank(message = "Metadata is required")
    private String  metaData;

    public Long getAnalyticLogId() {
        return analyticLogId;
    }

    public void setAnalyticLogId(Long analyticLogId) {
        this.analyticLogId = analyticLogId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public LocalDateTime getEventTimestamp() {
        return eventTimestamp;
    }

    public void setEventTimestamp(LocalDateTime eventTimestamp) {
        this.eventTimestamp = eventTimestamp;
    }

    public String getMetaData() {
        return metaData;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }
}
