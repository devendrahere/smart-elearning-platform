package com.edusmart.service.implemeted;

import com.edusmart.dto.QuizAttemptDTO;
import com.edusmart.dto.QuizDTO;
import com.edusmart.dto.QuizResultDTO;
import com.edusmart.entity.Courses;
import com.edusmart.entity.Quiz;
import com.edusmart.entity.Users;
import com.edusmart.exception.ResourcesNotFound;
import com.edusmart.repository.CourseRepository;
import com.edusmart.repository.QuizRepository;
import com.edusmart.repository.UserRepository;
import com.edusmart.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuizServiceImple implements QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    private final Map<Long,List<QuizResultDTO>> userResult=new HashMap<>();


    @Override
    public QuizDTO createQuiz(QuizDTO quizDTO) {
        Courses course= courseRepository.findById(quizDTO.getCourseId()).orElseThrow(()->new ResourcesNotFound("Course not found with id "+quizDTO.getCourseId()));

        Quiz quiz =new Quiz();
        quiz.setQuizTitle(quizDTO.getQuizTitle());
        quiz.setDescription(quizDTO.getDescription());
        quiz.setCourse(course);
        quiz.setCreatedAt(LocalDateTime.now());

        Quiz saved=quizRepository.save(quiz);
        return mapToDTO(saved);
    }

    @Override
    public QuizDTO getQuizById(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(()->new ResourcesNotFound("Quiz not found with quiz id : "+quizId));
        return mapToDTO(quiz);
    }

    @Override
    public List<QuizDTO> getAllQuizzes() {
        return quizRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public QuizResultDTO attemptQuiz(Long quizId, QuizAttemptDTO quizAttempt) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(()->new ResourcesNotFound("quiz not found with id: "+quizId));

        Users user= userRepository.findById(quizAttempt.getUserId())
                .orElseThrow(()->new ResourcesNotFound("user not found with user id: "+quizAttempt.getUserId()));

        Map<Long,String > submitted=quizAttempt.getAnswers();

        int totalQuestions=submitted.size();
        int correct=new Random().nextInt(totalQuestions+1);

        double score=correct*10.0;
        boolean passed=(correct>=totalQuestions*0.6);

        QuizResultDTO resultDTO=new QuizResultDTO();
        resultDTO.setQuizId(quizId);
        resultDTO.setScore((int)score);
        resultDTO.setPassed(passed);
        resultDTO.setUserId(user.getUserId());

        //save result in memory
        userResult.computeIfAbsent(user.getUserId(),k->new ArrayList<>()).add(resultDTO);

        return resultDTO;
    }

    @Override
    public List<QuizResultDTO> getResultByUser(Long userId) {
        return userResult.getOrDefault(userId,new ArrayList<>());
    }

    //mapping helper

    private QuizDTO mapToDTO(Quiz quiz){
        QuizDTO dto=new QuizDTO();
        dto.setQuizId(quiz.getQuizId());
        dto.setQuizTitle(quiz.getQuizTitle());
        dto.setDescription(quiz.getDescription());
        dto.setCourseId(quiz.getCourse().getCourseId());

        return dto;
    }
}
