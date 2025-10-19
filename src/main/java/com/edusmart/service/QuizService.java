package com.edusmart.service;

import com.edusmart.dto.QuizAttemptDTO;
import com.edusmart.dto.QuizDTO;
import com.edusmart.dto.QuizResultDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuizService {
    QuizDTO createQuiz(QuizDTO quizDTO);
    QuizDTO getQuizById(Long quizId);
    List<QuizDTO> getAllQuizzes();
    QuizResultDTO attemptQuiz(Long quizId, QuizAttemptDTO quizAttempt);
    List<QuizResultDTO> getResultByUser(Long userId);
}
