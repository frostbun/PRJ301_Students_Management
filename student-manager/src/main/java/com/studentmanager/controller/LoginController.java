package com.studentmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.studentmanager.dto.LoginDTO;
import com.studentmanager.model.User;
import com.studentmanager.service.SessionService;
import com.studentmanager.service.UserService;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private SessionService sessionService;

    @GetMapping
    public String get(Model view, @RequestParam(defaultValue = "") String error) {
        User user = sessionService.getCurrentUser();
        if (user != null) {
            return "redirect:/";
        }
        view.addAttribute("error", error);
        return "login";
    }

    @PostMapping
    public String post(Model view, LoginDTO dto) {
        User user = sessionService.getCurrentUser();
        if (user != null) {
            return "redirect:/";
        }
        String error = userService.login(dto);
        if (error != null) {
            view.addAttribute("username", dto.getUsername());
            view.addAttribute("error", error);
            return "login";
        }
        sessionService.setCurrentUser(dto.getUsername());
        return "redirect:/";
    }
}
