package com.studentmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.studentmanager.model.User;
import com.studentmanager.service.UserService;

@Controller
@RequestMapping("/login")
public class Login {
    @Autowired
    private UserService userService;

    @GetMapping
    public String get() {
        return "login";
    }

    @PostMapping
    public String post(Model view, User user) {
        if (!userService.login(user)) {
            view.addAttribute("error", "Wrong username or password");
            return "login";
        }
        return "redirect:/";
    }
}
