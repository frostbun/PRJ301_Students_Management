package com.studentmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.studentmanager.service.ClassroomService;
import com.studentmanager.service.SessionService;

@Controller
@RequestMapping("/class")
public class ClassController {
    @Autowired
    private SessionService session;
    @Autowired
    private ClassroomService classroomService;

    @GetMapping("/create")
    public String createGet() {
        if (!session.loggedIn()) {
            return "redirect:/login";
        }
        return "create_class";
    }

    @PostMapping("/create")
    public String createPost(Model view, String name) {
        if (!session.loggedIn()) {
            return "redirect:/login";
        }
        Long id = classroomService.create(name);
        if (id == null) {
            return "index";
        }
        return "redirect:/class/" + id;
    }

    @PostMapping("/join")
    public String joinPost(Model view, String inviteCode) {
        if (!session.loggedIn()) {
            return "redirect:/login";
        }
        Long id = classroomService.join(view, inviteCode);
        if (id == null) {
            return "index";
        }
        return "redirect:/class/" + id;
    }

    @GetMapping("/{id}/members")
    public String members(Model view, @PathVariable Long id, @RequestParam(defaultValue = "0") int page) {
        if (!session.loggedIn()) {
            return "redirect:/login";
        }
        view.addAttribute("members", classroomService.getClassroomMembers(id, page, 25));
        return "members";
    }
}
