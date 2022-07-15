package com.studentmanager.controller;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.studentmanager.config.PagingConfig;
import com.studentmanager.dto.CreateClassroomDTO;
import com.studentmanager.dto.ServiceResponse;
import com.studentmanager.model.Account;
import com.studentmanager.model.Classroom;
import com.studentmanager.service.ClassMemberService;
import com.studentmanager.service.ClassroomService;
import com.studentmanager.service.SessionService;

import javafx.util.Pair;

@Controller
@RequestMapping("/classroom")
public class ClassroomController {
    @Autowired
    private SessionService session;
    @Autowired
    private ClassroomService classroomService;
    @Autowired
    private ClassMemberService classMemberService;

    @GetMapping
    public String list(Model view, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "") String query, @RequestParam(defaultValue = "") String error) {
        Account account = session.getCurrentAccount();
        Long classCount = classMemberService.countClassrooms(account, query);
        view.addAttribute(
            "classes",
            classMemberService
                .getClassrooms(account, query, page-1, PagingConfig.SIZE)
                .stream()
                .map(classroom -> new Pair<>(classroom, classMemberService.getTeachers(classroom, "", 0, 3)))
                .collect(Collectors.toList())
        );
        view.addAttribute("classCount", classCount);
        view.addAttribute("page", page);
        view.addAttribute("query", query);
        view.addAttribute("pageCount", PagingConfig.pageCountOf(classCount));
        view.addAttribute("error", error);
        return "classroom";
    }

    @PostMapping("/create")
    public String create(RedirectAttributes redirect, CreateClassroomDTO dto) {
        ServiceResponse<Classroom> response = classroomService.create(session.getCurrentAccount(), dto);
        if (response.isError()) {
            redirect.addAttribute("error", response.getError());
            return "redirect:/classroom";
        }
        return "redirect:/classroom/" + response.getResponse().getId() + "/homework";
    }

    @PostMapping("/join")
    public String join(RedirectAttributes redirect, String inviteCode) {
        ServiceResponse<Classroom> response = classroomService.join(session.getCurrentAccount(), inviteCode);
        if (response.isError()) {
            redirect.addAttribute("error", response.getError());
            return "redirect:/classroom";
        }
        return "redirect:/classroom/" + response.getResponse().getId() + "/homework";
    }
}
