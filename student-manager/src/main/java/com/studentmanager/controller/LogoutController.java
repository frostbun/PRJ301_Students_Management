package com.studentmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.studentmanager.service.SessionService;

@Controller
@RequestMapping("/logout")
public class LogoutController {
    @Autowired
    private SessionService sessionService;

    @GetMapping
    public String get() {
        sessionService.setCurrentUser(null);
        return "redirect:/";
    }
}
