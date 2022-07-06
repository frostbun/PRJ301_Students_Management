package com.studentmanager.controller;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.studentmanager.config.PagingConfig;
import com.studentmanager.dto.CreateClassroomDTO;
import com.studentmanager.dto.ServiceResponse;
import com.studentmanager.model.Account;
import com.studentmanager.model.ClassMember;
import com.studentmanager.model.Classroom;
import com.studentmanager.service.ClassMemberService;
import com.studentmanager.service.ClassroomService;
import com.studentmanager.service.SessionService;

import javafx.util.Pair;

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
    public String list(Model view, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "") String query) {
        session.setCurrentAccount(Account.builder().username("admin1").build());
        Account account = session.getCurrentAccount();
        if (account == null) {
            return "redirect:/login";
        }
        view.addAttribute("classes", classMemberService.getClassrooms(account, query, page-1, PagingConfig.SIZE));
        view.addAttribute(
            "classes",
            classMemberService
                .getClassrooms(account, query, page-1, PagingConfig.SIZE)
                .stream()
                .map(classroom -> new Pair<>(classroom, classMemberService.getMembersByRole(classroom, ClassMember.TEACHER, 0, 3)))
                .collect(Collectors.toList())
        );
        Long classCount = classMemberService.countClassrooms(account, query);
        view.addAttribute("classCount", classCount);
        view.addAttribute("page", page);
        view.addAttribute("pageCount", PagingConfig.pageCountOf(classCount));
        return "class";
    }

    @PostMapping("/create")
    public String create(RedirectAttributes redirect, CreateClassroomDTO dto) {
        Account account = session.getCurrentAccount();
        if (account == null) {
            return "redirect:/login";
        }
        ServiceResponse<Classroom> response = classroomService.create(account, dto);
        if (response.isError()) {
            redirect.addAttribute("error", response.getError());
            return "redirect:/class/";
        }
        return "redirect:/class/" + response.getResponse().getId() + "/homework";
    }

    @PostMapping("/join")
    public String join(RedirectAttributes redirect, String inviteCode) {
        Account account = session.getCurrentAccount();
        if (account == null) {
            return "redirect:/login";
        }
        ServiceResponse<Classroom> response = classroomService.join(account, inviteCode);
        if (response.isError()) {
            redirect.addAttribute("error", response.getError());
            return "redirect:/class/";
        }
        return "redirect:/class/" + response.getResponse().getId() + "/homework";
    }

    @GetMapping("/{cid}/member")
    public String member(Model view, @PathVariable Long cid, @RequestParam(defaultValue = "1") int page) {
        Classroom classroom = classMemberService.getClassroom(session.getCurrentAccount(), cid);
        if (classroom == null) {
            return "redirect:/login";
        }
        view.addAttribute("members", classMemberService.getMembers(classroom, page-1, PagingConfig.SIZE));
        view.addAttribute("page", page);
        view.addAttribute("pageCount", PagingConfig.pageCountOf(classMemberService.countMembers(classroom)));
        return "member";
    }

    // @GetMapping("/{cid}/remove/{username}")
    // public String member(Model view, @PathVariable Long cid, @RequestParam(defaultValue = "1") int page) {
    //     Classroom classroom = classMemberService.getClassroom(session.getCurrentAccount(), cid);
    //     if (classroom == null) {
    //         return "redirect:/login";
    //     }
    //     view.addAttribute("members", classMemberService.getMembers(classroom, page-1, PagingConfig.SIZE));
    //     view.addAttribute("page", page);
    //     view.addAttribute("pageCount", PagingConfig.pageCountOf(classMemberService.countMembers(classroom)));
    //     return "member";
    // }

    // @GetMapping("/{cid}/leave")
    // public String leave(Model view, @PathVariable Long cid, @RequestParam(defaultValue = "1") int page) {
    //     Classroom classroom = classMemberService.getClassroom(session.getCurrentAccount(), cid);
    //     if (classroom == null) {
    //         return "redirect:/login";
    //     }
    //     view.addAttribute("members", classMemberService.getMembers(classroom, page, PagingConfig.SIZE));
    //     view.addAttribute("pages", classMemberService.countMembers(classroom) / PagingConfig.SIZE);
    //     return "member";
    // }

    // @GetMapping("/{cid}/delete")
    // public String delete(Model view, @PathVariable Long cid, @RequestParam(defaultValue = "1") int page) {
    //     Classroom classroom = classMemberService.getClassroom(session.getCurrentAccount(), cid);
    //     if (classroom == null) {
    //         return "redirect:/login";
    //     }
    //     view.addAttribute("members", classMemberService.getMembers(classroom, page, PagingConfig.SIZE));
    //     view.addAttribute("pages", classMemberService.countMembers(classroom) / PagingConfig.SIZE);
    //     return "member";
    // }
}
