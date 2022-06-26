package com.studentmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.studentmanager.service.SessionService;

@Controller
@RequestMapping("/")
public class IndexController {
    @Autowired
    private SessionService session;

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/logout")
    public String logout() {
        session.setCurrentAccount(null);
        return "redirect:/";
    }
}
