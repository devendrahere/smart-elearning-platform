package com.edusmart.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class CourseDTO {
    private Long courseId;
    @NotBlank(message = "Course Title cannot be empty")
    @Size(min = 3,max = 100 ,message = "The course title should be of length in between 3 and 100")
    private String courseTitle;
    @Size(max = 200,message = "The description should under 200 characters")
    private String courseDescription;

    private LocalDateTime createdAt;

    @NotBlank(message = "Instructors Details are required")
    private UserSummaryDTO instructor;

    private List<CategoryDTO> categories;


    private List<LessonDTO> lessons;
    private List<EnrollmentsDTO> enrollments;
    private List<FileResourceDTO> materials;

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UserSummaryDTO getInstructor() {
        return instructor;
    }

    public void setInstructor(UserSummaryDTO instructor) {
        this.instructor = instructor;
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDTO> categories) {
        this.categories = categories;
    }

    public List<LessonDTO> getLessons() {
        return lessons;
    }

    public void setLessons(List<LessonDTO> lessons) {
        this.lessons = lessons;
    }

    public List<EnrollmentsDTO> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<EnrollmentsDTO> enrollments) {
        this.enrollments = enrollments;
    }

    public List<FileResourceDTO> getMaterials() {
        return materials;
    }

    public void setMaterials(List<FileResourceDTO> materials) {
        this.materials = materials;
    }
}
