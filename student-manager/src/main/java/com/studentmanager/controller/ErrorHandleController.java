package com.studentmanager.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandleController {

    @ExceptionHandler(NullPointerException.class)
    public String handleException(Exception e) {
        return "redirect:/error/404";
    }
}
