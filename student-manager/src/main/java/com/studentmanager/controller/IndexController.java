package com.studentmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.studentmanager.service.ClassroomService;
import com.studentmanager.service.SessionService;

@Controller
@RequestMapping("/")
public class IndexController {
    @Autowired
    private SessionService session;
    @Autowired
    private ClassroomService classroomService;

    @GetMapping
    public String index(Model view, @RequestParam(defaultValue = "0") int page) {
        if (session.loggedIn()) {
            view.addAttribute("classes", classroomService.getJoinedClassrooms(page, 10));
        }
        return "index";
    }

    @GetMapping("/logout")
    public String logout() {
        session.setCurrentAccount(null);
        return "redirect:/";
    }
}
