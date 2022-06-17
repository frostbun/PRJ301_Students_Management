package com.studentmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.studentmanager.model.Class;
import com.studentmanager.service.ClassService;
import com.studentmanager.service.UserService;

@Controller
@RequestMapping("/class")
public class ClassController {
    @Autowired
    private UserService userService;
    @Autowired
    private ClassService classService;

    @GetMapping("/create")
    public String create() {
        return "PLACEHOLDER";
    }

    @PostMapping("/create")
    public String createSubmit(Model view, Class _class) {
        if (!classService.createClass(_class)) {
            view.addAttribute("error", "Wrong username or password");
            return "login";
        }
        return "redirect:/class";
    }

    @GetMapping("/{id}")
    public String homeworks(Model view, @PathVariable int id) {
        view.addAttribute("title", id);
        return "class";
    }

    @GetMapping("/{id}/members")
    public String members(Model view, @PathVariable int id, @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        view.addAttribute("members", classService.viewClassMembers(id, page, size));
        return "class";
    }
}
