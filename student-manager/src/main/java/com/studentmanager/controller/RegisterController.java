package com.studentmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.studentmanager.dto.RegisterDTO;
import com.studentmanager.model.User;
import com.studentmanager.service.SessionService;
import com.studentmanager.service.UserService;

@Controller
@RequestMapping("/register")
public class RegisterController {
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
        return "register";
    }

    @PostMapping
    public String post(Model view, RegisterDTO dto) {
        User user = sessionService.getCurrentUser();
        if (user != null) {
            return "redirect:/";
        }
        String error = userService.register(dto);
        if (error != null) {
            view.addAttribute("username", dto.getUsername());
            view.addAttribute("firstName", dto.getFirstName());
            view.addAttribute("lastName", dto.getLastName());
            view.addAttribute("phone", dto.getPhone());
            view.addAttribute("email", dto.getEmail());
            view.addAttribute("address", dto.getAddress());
            view.addAttribute("error", error);
            return "register";
        }
        sessionService.setCurrentUser(dto.getUsername());
        return "redirect:/";
    }
}
