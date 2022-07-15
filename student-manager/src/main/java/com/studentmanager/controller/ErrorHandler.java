package com.studentmanager.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({ NullPointerException.class, IllegalArgumentException.class })
    public String handleException(Exception e) {
        return "redirect:/error/404";
    }
}
