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
@RequestMapping("/register")
public class Register {
    @Autowired
    public UserService userService;

    @GetMapping
    public String get() {
        return "register";
    }

    @PostMapping
    public String post(Model view, User user) {
        view.addAttribute("error", userService.register(user));
        return "register";
    }
}
