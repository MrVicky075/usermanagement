package com.company.usermanagement.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handelNotFound(ResourceNotFoundException ex, Model model){
        model.addAttribute("message",ex.getMessage());
        return "error/404";
    }

    @ExceptionHandler(BusinessException.class)
    public String handleBusiness(BusinessException ex, Model model){
        model.addAttribute("message",ex.getMessage());
        return "error/400";
    }

    @ExceptionHandler(Exception.class)
    public String handelException(Exception ex, Model model){
        model.addAttribute("message", ex.getMessage());
        return "error/500";
    }
}
