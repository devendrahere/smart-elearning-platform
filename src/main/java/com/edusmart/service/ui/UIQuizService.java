package com.edusmart.service.ui;

import com.edusmart.dto.QuestionDTO;
import com.edusmart.dto.QuizDTO;
import com.edusmart.dto.QuizAttemptDTO;
import com.edusmart.dto.QuizResultDTO;
import com.edusmart.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class UIQuizService {

    @Autowired
    private QuizService quizService;

    // 📚 Get all quizzes
    public List<QuizDTO> getAllQuizzes() {
        return quizService.getAllQuizzes();
    }

    // 🔍 Get quiz by ID
    public QuizDTO getQuiz(Long quizId) {
        return quizService.getQuizById(quizId);
    }

    // 🧠 Submit quiz attempt (student)
    public QuizResultDTO submitQuiz(Long quizId, Long userId, QuizAttemptDTO attemptDTO) {
        attemptDTO.setUserId(userId);
        attemptDTO.setQuizId(quizId);
        return quizService.attemptQuiz(quizId, attemptDTO);
    }

    // 📊 Get results for a user
    public List<QuizResultDTO> getResultsForUser(Long userId) {
        return quizService.getResultByUser(userId);
    }

    // 💾 Create or update a quiz (Instructor)
    public QuizDTO saveQuiz(QuizDTO quizDTO) {
        // Backend handles create/update automatically
        return quizService.createQuiz(quizDTO);
    }
    public List<QuestionDTO> getQuestionsForQuiz(Long quizId) {
        return quizService.getQuestionsByQuizId(quizId);
    }
    public QuizResultDTO getLastResultForCourse(Long userId, Long courseId) {
        List<QuizResultDTO> allResults = quizService.getResultByUser(userId);
        return allResults.stream()
                .filter(r -> {
                    QuizDTO quiz = quizService.getQuizById(r.getQuizId());
                    return quiz.getCourseId().equals(courseId);
                })
                .max(Comparator.comparing(QuizResultDTO::getQuizId)) // or by attempt timestamp if available
                .orElse(null);
    }
}
