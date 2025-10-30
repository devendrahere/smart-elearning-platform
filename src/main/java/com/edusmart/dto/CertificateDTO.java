package com.edusmart.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class CertificateDTO {
    private Long certificateId;
    @NotNull(message = "User Id is required")
    private Long userId;
    @NotNull(message = "Course Id is required")
    private Long courseId;
    @NotBlank(message = "Course Title is required")
    private String courseTitle;

    private LocalDateTime issueDate;

    @NotBlank(message = "Certificate code is required")
    private String certificateCode;

    @NotBlank(message = "PDF URL is required")
    private String pdfURl;


    public Long getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(Long certificateId) {
        this.certificateId = certificateId;
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

    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDateTime issueDate) {
        this.issueDate = issueDate;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCertificateCode() {
        return certificateCode;
    }

    public void setCertificateCode(String certificateCode) {
        this.certificateCode = certificateCode;
    }

    public String getPdfURl() {
        return pdfURl;
    }

    public void setPdfURl(String pdfURl) {
        this.pdfURl = pdfURl;
    }
}
