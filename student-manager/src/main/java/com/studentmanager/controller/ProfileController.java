package com.studentmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.studentmanager.dto.ChangeUserInformationDTO;
import com.studentmanager.dto.ChangeUserPasswordDTO;
import com.studentmanager.model.User;
import com.studentmanager.service.SessionService;
import com.studentmanager.service.UserService;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private UserService userService;
    @Autowired
    private SessionService sessionService;

    @GetMapping
    public String profile(Model view) {
        User user = sessionService.getCurrentUser();
        if (user == null) {
            return "redirect:/";
        }
        return "profile";
    }

    @GetMapping("/{id}")
    public String profileById(Model view, @PathVariable int id) {
        User user = sessionService.getCurrentUser();
        if (user == null) {
            return "redirect:/";
        }
        return "profile";
    }

    @GetMapping("/edit")
    public String edit(Model view, @RequestParam(defaultValue = "") String error) {
        User user = sessionService.getCurrentUser();
        if (user == null) {
            return "redirect:/";
        }
        view.addAttribute("error", error);
        return "editProfile";
    }
    
    @PostMapping("/edit/information")
    public String changeInformation(Model view, ChangeUserInformationDTO dto) {
        User user = sessionService.getCurrentUser();
        if (user == null) {
            return "redirect:/";
        }
        String error = userService.changeInformation(user.getUsername(), dto);
        if (error != null) {
            view.addAttribute("firstName", dto.getFirstName());
            view.addAttribute("lastName", dto.getLastName());
            view.addAttribute("phone", dto.getPhone());
            view.addAttribute("email", dto.getEmail());
            view.addAttribute("address", dto.getAddress());
            view.addAttribute("error", error);
            return "editProfile";
        }
        return "redirect:/profile";
    }

    @PostMapping("/edit/password")
    public String changePassword(Model view, ChangeUserPasswordDTO dto) {
        User user = sessionService.getCurrentUser();
        if (user == null) {
            return "redirect:/";
        }
        String error = userService.changePassword(user.getUsername(), dto);
        if (error != null) {
            view.addAttribute("error", error);
            return "editProfile";
        }
        return "redirect:/profile";
    }
}