package com.edusmart.service.implemeted;

import com.edusmart.dto.QuestionDTO;
import com.edusmart.entity.Question;
import com.edusmart.entity.Quiz;
import com.edusmart.exception.ResourcesNotFound;
import com.edusmart.repository.QuestionRepository;
import com.edusmart.repository.QuizRepository;
import com.edusmart.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Override
    public QuestionDTO addQuestion(QuestionDTO dto) {
        Quiz quiz = quizRepository.findById(dto.getQuizId())
                .orElseThrow(() -> new ResourcesNotFound("Quiz not found with id: " + dto.getQuizId()));

        Question q = new Question();
        q.setQuiz(quiz);
        q.setQuestionText(dto.getQuestionText());
        q.setOptionA(dto.getOptionA());
        q.setOptionB(dto.getOptionB());
        q.setOptionC(dto.getOptionC());
        q.setOptionD(dto.getOptionD());
        q.setCorrectOption(dto.getCorrectOption());

        Question saved = questionRepository.save(q);
        dto.setQuestionId(saved.getQuestionId());
        return dto;
    }

    @Override
    public List<QuestionDTO> getQuestionsByQuiz(Long quizId) {
        return questionRepository.findByQuiz_QuizId(quizId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteQuestion(Integer questionId) {
        questionRepository.deleteById(questionId);
    }

    private QuestionDTO mapToDTO(Question q) {
        QuestionDTO dto = new QuestionDTO();
        dto.setQuestionId(q.getQuestionId());
        dto.setQuizId(q.getQuiz().getQuizId());
        dto.setQuestionText(q.getQuestionText());
        dto.setOptionA(q.getOptionA());
        dto.setOptionB(q.getOptionB());
        dto.setOptionC(q.getOptionC());
        dto.setOptionD(q.getOptionD());
        dto.setCorrectOption(q.getCorrectOption());
        return dto;
    }
}
