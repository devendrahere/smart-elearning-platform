package com.edusmart.dto;

import java.time.LocalDateTime;
import java.util.Set;

public class CourseSummaryDTO {
    private long courseId;
    private String courseName;
    private String courseDescription;
    private String instructorName;
    private Set<CategoryDTO> categoryName;
    private LocalDateTime createdAt;

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Set<CategoryDTO> getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(Set<CategoryDTO> categoryName) {
        this.categoryName = categoryName;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
