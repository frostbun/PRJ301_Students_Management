package com.studentmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.studentmanager.dto.RegisterDTO;
import com.studentmanager.service.SessionService;
import com.studentmanager.service.AccountService;

@Controller
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private SessionService session;

    @GetMapping
    public String get(Model view) {
        if (session.loggedIn()) {
            return "redirect:/";
        }
        return "register";
    }

    @PostMapping
    public String post(Model view, RegisterDTO dto) {
        if (session.loggedIn()) {
            return "redirect:/";
        }
        if (!accountService.register(view, dto)) {
            dto.addToView(view);
            return "register";
        }
        return "redirect:/";
    }
}
