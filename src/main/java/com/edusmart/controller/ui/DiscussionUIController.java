package com.edusmart.controller.ui;

import com.edusmart.service.ui.UIDiscussionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/discussion")
public class DiscussionUIController {
    @Autowired
    private UIDiscussionService discussionService;

    @GetMapping("/courseId/{courseId}")
    public String courseDiscussions(@PathVariable Long courseId, Model model){
        model.addAttribute("threads",discussionService.getDiscussionByCourse(courseId));
        return  "discussion";
    }
}