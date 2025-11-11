package com.edusmart.controller.ui;

import com.edusmart.dto.QuestionDTO;
import com.edusmart.service.QuestionService;
import com.edusmart.service.ui.UIQuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/quiz/questions")
public class ManageQuestionUIController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UIQuizService quizService;

    // List all questions for a quiz
    @GetMapping("/{quizId}")
    public String listQuestions(@PathVariable Long quizId, Model model) {
        model.addAttribute("quiz", quizService.getQuiz(quizId));
        model.addAttribute("questions", questionService.getQuestionsByQuiz(quizId));

        QuestionDTO newQuestion = new QuestionDTO();
        newQuestion.setQuizId(quizId); // ✅ This is critical
        model.addAttribute("newQuestion", newQuestion);

        return "manage-questions"; // Thymeleaf view
    }

    // Add a new question
    @PostMapping("/add")
    public String addQuestion(@ModelAttribute("newQuestion") QuestionDTO dto, Model model) {
        if (dto.getQuizId() == null) {
            model.addAttribute("errorMessage", "Quiz ID is missing — please reload the page.");
            return "manage-questions";
        }
        questionService.addQuestion(dto);
        return "redirect:/quiz/questions/" + dto.getQuizId();
    }

    // Delete a question
    @PostMapping("/delete/{questionId}")
    public String deleteQuestion(@PathVariable Integer questionId, @RequestParam Long quizId) {
        questionService.deleteQuestion(questionId);
        return "redirect:/quiz/questions/" + quizId;
    }


}