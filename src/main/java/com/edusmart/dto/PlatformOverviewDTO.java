package com.edusmart.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;
import java.util.List;

public class PlatformOverviewDTO {
    @PositiveOrZero(message = "Total users cannot be negative")
    private Long totalUsers;
    @PositiveOrZero(message = "Active users cannot be negative")
    private Long activeUsers;
    @PositiveOrZero(message = "Total courses cannot be negative")
    private Long totalCourse;
    @NotNull(message = "Average Completion rate is required")
    private Double averageCompletionRate;
    @NotNull(message = "Total completion is required")
    private Long totalCompletion;
    @NotNull(message = "Top courses are required")
    private List<String> topCourses;
    @PositiveOrZero(message = "Active sessions cannot be negative")
    private Long activeSessions = 0L;

    public Long getActiveSessions() {
        return activeSessions;
    }

    public void setActiveSessions(Long activeSessions) {
        this.activeSessions = activeSessions;
    }


    private LocalDateTime timestamp;

    public Long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(Long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public Long getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(Long activeUsers) {
        this.activeUsers = activeUsers;
    }

    public Long getTotalCourse() {
        return totalCourse;
    }

    public void setTotalCourse(Long totalCourse) {
        this.totalCourse = totalCourse;
    }

    public Double getAverageCompletionRate() {
        return averageCompletionRate;
    }

    public void setAverageCompletionRate(Double averageCompletionRate) {
        this.averageCompletionRate = averageCompletionRate;
    }

    public Long getTotalCompletion() {
        return totalCompletion;
    }

    public void setTotalCompletion(Long totalCompletion) {
        this.totalCompletion = totalCompletion;
    }

    public List<String> getTopCourses() {
        return topCourses;
    }

    public void setTopCourses(List<String> topCourses) {
        this.topCourses = topCourses;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
