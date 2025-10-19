package com.edusmart.controller;

import com.edusmart.dto.QuizAttemptDTO;
import com.edusmart.dto.QuizDTO;
import com.edusmart.dto.QuizResultDTO;
import com.edusmart.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    @Autowired
    private QuizService quizService;

    //creating a new quiz
    //url /api/quizzes/
    @PostMapping("/")
    public ResponseEntity<QuizDTO> createQuiz(@RequestBody QuizDTO quiz){
        return ResponseEntity.status(HttpStatus.CREATED).body(quizService.createQuiz(quiz));
    }

    //fetching quiz by quizId
    //url /api/quizzes/{quizId}
    @GetMapping("/{quizId}")
    public  ResponseEntity<QuizDTO> getQuizById(@PathVariable Long quizId){
        return ResponseEntity.ok(quizService.getQuizById(quizId));
    }

    //fetching all quizzes
    //url /api/quizzes/
    @GetMapping("/")
    public ResponseEntity<List<QuizDTO>> getAllQuizzes(){
        return ResponseEntity.ok(quizService.getAllQuizzes());
    }

    //creating a new quiz result by passing new quizAttempt and quizId
    //url /api/quizzes/{quizId}/attempt
    @PostMapping("/{quizId}/attempt")
    public ResponseEntity<QuizResultDTO> attemptQuiz(@PathVariable Long quizId, @RequestBody QuizAttemptDTO quizAttempt){
        return ResponseEntity.ok(quizService.attemptQuiz(quizId,quizAttempt));
    }

    //fetching the result of user by user id
    //url /api/quizzes/result/user/{userId}
    @GetMapping("/results/user/{userId}")
    public ResponseEntity<List<QuizResultDTO>> getResultByUser(@PathVariable Long userId){
        return ResponseEntity.ok(quizService.getResultByUser(userId));
    }
}
