package com.edusmart.controller.ui;

import com.edusmart.service.ui.UIAnalyticsLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/analytics")
public class AnalyticUIController {
    @Autowired
    private UIAnalyticsLogService analyticsLogService;

    @GetMapping
    public String analyticsDashboard(Model model){
        model.addAttribute("stats",analyticsLogService.getPlatformOverview());
        return "analytics";
    }
    @GetMapping("/{userId}")
    public String studentAnalytics(@PathVariable Long userId,Model model){
        model.addAttribute("student_stats",analyticsLogService.getUserProgressAnalytics(userId));
        return "student-analytics";
    }
}
