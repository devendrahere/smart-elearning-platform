package com.edusmart.dto;


import jakarta.validation.constraints.NotNull;

import java.util.Map;

public class QuizAttemptDTO {
    @NotNull(message = "User Id is required")
    private Long userId;
    @NotNull(message = "Quiz Id is required")
    private Long quizId;
    @NotNull(message = "The Attempts are required")
    private Map<Long,String> answers;

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Map<Long, String> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<Long, String> answers) {
        this.answers = answers;
    }
}
