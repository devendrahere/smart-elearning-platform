package com.edusmart.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

public class EnrollmentsDTO {
    private Long enrollmentId;
    private LocalDateTime enrolledAt;
    @NotNull(message = "Progress is required")
    private Double progress;
    @NotBlank(message = "Status is required")
    @Pattern(
            regexp = "ACTIVE|COMPLETED|CANCELLED",
            message = "status must be one of: ACTIVE,COMPLETED,CANCELLED"
    )
    private String status;
    @Valid
    @NotNull(message = "Course Details are required")
    private CourseSummaryDTO course;

    @Valid
    @NotNull(message = "User details are required")
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
