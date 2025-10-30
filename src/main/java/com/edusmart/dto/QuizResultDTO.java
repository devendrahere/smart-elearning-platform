package com.edusmart.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class QuizResultDTO {
    @NotNull(message = "Quiz Id is required")
    private Long quizId;
    @NotNull(message = "User Id is required")
    private Long userId;
    @PositiveOrZero(message = "Score needs to be valid")
    private int score;
    private boolean passed;

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

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
