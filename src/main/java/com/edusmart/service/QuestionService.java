package com.edusmart.service;

import com.edusmart.dto.QuestionDTO;
import java.util.List;

public interface QuestionService {
    QuestionDTO addQuestion(QuestionDTO questionDTO);
    List<QuestionDTO> getQuestionsByQuiz(Long quizId);
    void deleteQuestion(Integer questionId);
}
