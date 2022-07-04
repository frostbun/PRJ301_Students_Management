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
import com.studentmanager.dto.ServiceResponse;
import com.studentmanager.model.Account;
import com.studentmanager.model.Classroom;
import com.studentmanager.service.ClassMemberService;
import com.studentmanager.service.ClassroomService;
import com.studentmanager.service.SessionService;

@Controller
@RequestMapping("/class")
public class ClassController {
    @Autowired
    private SessionService session;
    @Autowired
    private ClassroomService classroomService;
    @Autowired
    private ClassMemberService classMemberService;

    @GetMapping
    public String list(Model view, @RequestParam(defaultValue = "0") int page) {
        Account account = session.getCurrentAccount();
        if (account == null) {
            return "redirect:/";
        }
        view.addAttribute("classes", classMemberService.getClassrooms(account, page, PagingConfig.SIZE));
        view.addAttribute("pages", classMemberService.countClassrooms(account) / PagingConfig.SIZE);
        return "class_list";
    }

    @PostMapping("/create")
    public String create(Model view, String name) {
        Account account = session.getCurrentAccount();
        if (account == null) {
            return "redirect:/";
        }
        ServiceResponse<Classroom> response = classroomService.create(account, name);
        if (response.isError()) {
            view.addAttribute("error", response.getError());
            return "class";
        }
        return "redirect:/class/" + response.getResponse().getId();
    }

    @PostMapping("/join")
    public String join(Model view, String inviteCode) {
        Account account = session.getCurrentAccount();
        if (account == null) {
            return "redirect:/";
        }
        ServiceResponse<Classroom> response = classroomService.join(account, inviteCode);
        if (response.isError()) {
            view.addAttribute("error", response.getError());
            return "class";
        }
        return "redirect:/class/" + response.getResponse().getId();
    }

    @GetMapping("/{cid}/member")
    public String member(Model view, @PathVariable Long cid, @RequestParam(defaultValue = "0") int page) {
        Classroom classroom = classMemberService.getClassroom(session.getCurrentAccount(), cid);
        if (classroom == null) {
            return "redirect:/";
        }
        view.addAttribute("members", classMemberService.getMembers(classroom, page, PagingConfig.SIZE));
        view.addAttribute("pages", classMemberService.countMembers(classroom) / PagingConfig.SIZE);
        return "member";
    }

    // @GetMapping("/{cid}/leave")
    // public String leave(Model view, @PathVariable Long cid, @RequestParam(defaultValue = "0") int page) {
    //     Classroom classroom = classMemberService.getClassroom(session.getCurrentAccount(), cid);
    //     if (classroom == null) {
    //         return "redirect:/";
    //     }
    //     view.addAttribute("members", classMemberService.getMembers(classroom, page, PagingConfig.SIZE));
    //     view.addAttribute("pages", classMemberService.countMembers(classroom) / PagingConfig.SIZE);
    //     return "member";
    // }
}
