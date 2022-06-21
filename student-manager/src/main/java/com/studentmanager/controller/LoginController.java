package com.studentmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.studentmanager.dto.LoginDTO;
import com.studentmanager.service.AccountService;
import com.studentmanager.service.SessionService;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private SessionService session;

    @GetMapping
    public String get(Model view, @RequestParam(defaultValue = "") String error) {
        if (session.loggedIn()) {
            return "redirect:/";
        }
        return "login";
    }

    @PostMapping
    public String post(Model view, LoginDTO dto) {
        if (session.loggedIn()) {
            return "redirect:/";
        }
        if (!accountService.login(view, dto)) {
            dto.addToView(view);
            return "register";
        }
        return "redirect:/";
    }
}
