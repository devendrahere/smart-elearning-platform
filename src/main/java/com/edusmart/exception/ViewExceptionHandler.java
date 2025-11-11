package com.edusmart.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ViewExceptionHandler {

    @ExceptionHandler(ResourcesNotFound.class)
    public String handleResourceNotFound(ResourcesNotFound ex, Model model) {
        model.addAttribute("errorTitle", "Thread Not Found");
        model.addAttribute("errorMessage", ex.getMessage());
        return "thread-not-found";  // a Thymeleaf page
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleRuntime(RuntimeException ex, Model model) {
        model.addAttribute("errorTitle", "Something Went Wrong");
        model.addAttribute("errorMessage", ex.getMessage());
        return "thread-not-found";
    }
}
