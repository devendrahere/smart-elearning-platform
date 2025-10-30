package com.edusmart.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class QuizDTO {
    private Long quizId;
    @NotBlank(message = "Quiz Title is Required")
    @Size(min = 3,max = 100,message = "The Quiz Title length should be in 3 and 100.")
    private String quizTitle;
    @NotBlank(message = "Description is required")
    private String description;
    @NotNull(message = "Course Id is Required")
    private Long courseId;

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
