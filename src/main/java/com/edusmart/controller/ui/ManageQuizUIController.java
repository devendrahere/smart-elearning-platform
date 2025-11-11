package com.edusmart.controller.ui;

import com.edusmart.dto.QuizDTO;
import com.edusmart.service.ui.UIQuizService;
import com.edusmart.service.ui.UICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/quiz/manage")
public class ManageQuizUIController {

    @Autowired
    private UIQuizService quizService;

    @Autowired
    private UICourseService courseService;

    // 🆕 Show Add Quiz Form
    @GetMapping("/{courseId}")
    public String showAddQuizForm(@PathVariable Long courseId, Model model) {
        QuizDTO quiz = new QuizDTO();
        quiz.setCourseId(courseId);

        model.addAttribute("quiz", quiz);
        model.addAttribute("course", courseService.getCourseById(courseId));

        return "manage-quiz"; // templates/manage-quiz.html
    }

    // ✏️ Show Edit Quiz Form
    @GetMapping("/{courseId}/{quizId}")
    public String showEditQuizForm(@PathVariable Long courseId,
                                   @PathVariable Long quizId,
                                   Model model) {
        QuizDTO quiz = quizService.getQuiz(quizId);

        model.addAttribute("quiz", quiz);
        model.addAttribute("course", courseService.getCourseById(courseId));

        return "manage-quiz";
    }

    // 💾 Handle Save
    @PostMapping("/save")
    public String saveQuiz(@ModelAttribute("quiz") QuizDTO quizDTO) {
        quizService.saveQuiz(quizDTO);
        return "redirect:/courses/" + quizDTO.getCourseId() + "/lessons/manage";
    }

    // 🔄 Handle Update (reuse same form)
    @PostMapping("/update")
    public String updateQuiz(@ModelAttribute("quiz") QuizDTO quizDTO) {
        quizService.saveQuiz(quizDTO);
        return "redirect:/courses/" + quizDTO.getCourseId() + "/lessons/manage";
    }
}