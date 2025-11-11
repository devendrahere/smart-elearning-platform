package com.edusmart.controller.ui;

import com.edusmart.dto.QuestionDTO;
import com.edusmart.dto.QuizAttemptDTO;
import com.edusmart.dto.QuizDTO;
import com.edusmart.dto.QuizResultDTO;
import com.edusmart.service.UserService;
import com.edusmart.service.ui.UIQuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/quizzes")
public class QuizUIController {

    @Autowired
    private UIQuizService quizService;

    @Autowired
    private UserService userService;

    // 🧭 Show list of all quizzes
    @GetMapping
    public String listQuizzes(Model model) {
        List<QuizDTO> quizzes = quizService.getAllQuizzes();
        model.addAttribute("quizzes", quizzes);
        return "quiz-list"; // templates/quiz-list.html
    }

    // 📋 Show quiz details before starting
    @GetMapping("/{quizId}")
    public String showQuizDetails(@PathVariable Long quizId, Model model) {
        QuizDTO quiz = quizService.getQuiz(quizId);
        model.addAttribute("quiz", quiz);
        return "quiz-details"; // templates/quiz-details.html
    }

    // 🧠 Start quiz attempt page
    @GetMapping("/{quizId}/start")
    public String startQuiz(@PathVariable Long quizId, Authentication authentication, Model model) {
        QuizDTO quiz = quizService.getQuiz(quizId);
        List<QuestionDTO> questions = quizService.getQuestionsForQuiz(quizId);

        String username = authentication.getName();
        Long userId = userService.getUserByUsername(username).getUserId();

        model.addAttribute("quiz", quiz);
        model.addAttribute("questions", questions);
        model.addAttribute("userId", userId);

        return "quiz-attempts"; // templates/quiz-attempts.html
    }

    // 🏁 Submit quiz attempt (✅ Only one version now)
    @PostMapping("/{quizId}/submit")
    public String submitQuiz(
            @PathVariable Long quizId,
            Authentication authentication,
            @RequestParam Map<String, String> allParams,
            Model model) {

        String username = authentication.getName();
        Long userId = userService.getUserByUsername(username).getUserId();

        Map<Long, String> answers = new HashMap<>();
        allParams.forEach((key, value) -> {
            if (key.startsWith("question_")) {
                Long questionId = Long.valueOf(key.substring(9));
                answers.put(questionId, value);
            }
        });

        QuizAttemptDTO attemptDTO = new QuizAttemptDTO();
        attemptDTO.setAnswers(answers);

        QuizResultDTO result = quizService.submitQuiz(quizId, userId, attemptDTO);
        model.addAttribute("result", result);

        return "quiz-results"; // templates/quiz-results.html
    }

    // 📊 View previous results
    @GetMapping("/results/{userId}")
    public String viewResults(@PathVariable Long userId, Model model) {
        List<QuizResultDTO> results = quizService.getResultsForUser(userId);
        model.addAttribute("results", results);
        return "quiz-results"; // templates/quiz-results.html
    }
}