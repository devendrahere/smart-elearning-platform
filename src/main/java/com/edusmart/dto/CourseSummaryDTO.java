package com.edusmart.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.Set;

public class CourseSummaryDTO {

    private long courseId;
    @NotBlank
    @Size(min = 3,max = 100,message = "The course name should be of length in between 3 and 100")
    private String courseName;

    @Size(max = 200, message = "The course Description length must be under 200")
    private String courseDescription;

    @NotBlank(message = "Instructor's Name is required")
    private String instructorName;

    @NotNull(message = "Atleast one category is required")
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
