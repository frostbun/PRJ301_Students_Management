package com.studentmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.studentmanager.config.PagingConfig;
import com.studentmanager.model.Account;
import com.studentmanager.model.Classroom;
import com.studentmanager.service.ClassroomService;
import com.studentmanager.service.SessionService;

@Controller
@RequestMapping("/class")
public class ClassController {
    @Autowired
    private SessionService session;
    @Autowired
    private ClassroomService classroomService;

    @PostMapping("/create")
    public String create(Model view, String name) {
        Account account = session.getCurrentAccount();
        if (account == null) {
            return "redirect:/login";
        }
        Long id = classroomService.create(view, account, name);
        if (id == null) {
            return "index";
        }
        return "redirect:/class/" + id;
    }

    @PostMapping("/join")
    public String join(Model view, String inviteCode) {
        Account account = session.getCurrentAccount();
        if (account == null) {
            return "redirect:/login";
        }
        Long id = classroomService.join(view, account, inviteCode);
        if (id == null) {
            return "index";
        }
        return "redirect:/class/" + id;
    }

    @GetMapping("/{id}/members")
    public String members(Model view, @PathVariable Long id, @RequestParam(defaultValue = "0") int page) {
        Account account = session.getCurrentAccount();
        if (account == null) {
            return "redirect:/login";
        }
        Classroom classroom = classroomService.getClassroomById(id, account);
        if (classroom == null) {
            return "redirect:/";
        }
        view.addAttribute("members", classroomService.getClassroomMembers(classroom, page, PagingConfig.SIZE));
        view.addAttribute("pages", classroomService.countClassroomMembers(classroom) / PagingConfig.SIZE);
        return "members";
    }
}
