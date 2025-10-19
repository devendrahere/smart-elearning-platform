package com.edusmart.dto;

import java.time.LocalDateTime;

public class EnrollmentsDTO {
    private Long enrollmentId;
    private LocalDateTime enrolledAt;
    private Double progress;
    private String status;

    private CourseSummaryDTO course;
    private UserSummaryDTO student;

    public UserSummaryDTO getStudent() {
        return student;
    }

    public void setStudent(UserSummaryDTO student) {
        this.student = student;
    }

    public CourseSummaryDTO getCourse() {
        return course;
    }

    public void setCourse(CourseSummaryDTO course) {
        this.course = course;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getProgress() {
        return progress;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
    }

    public LocalDateTime getEnrolledAt() {
        return enrolledAt;
    }

    public void setEnrolledAt(LocalDateTime enrolledAt) {
        this.enrolledAt = enrolledAt;
    }

    public Long getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(Long enrollmentId) {
        this.enrollmentId = enrollmentId;
    }
}
