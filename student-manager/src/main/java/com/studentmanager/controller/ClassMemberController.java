package com.studentmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.studentmanager.config.PagingConfig;
import com.studentmanager.model.ClassMember;
import com.studentmanager.model.Classroom;
import com.studentmanager.service.ClassMemberService;

@Controller
@RequestMapping("/classroom/{cid}/member")
public class ClassMemberController {
    @Autowired
    private ClassMemberService classMemberService;

    @GetMapping
    public String list(
        Model view,
        @RequestAttribute Classroom classroom,
        @RequestAttribute int page,
        @RequestAttribute String query
    ) {
        Long memberCount = classMemberService.countClassMembers(classroom);
        view.addAttribute("members", classMemberService.getClassMembers(classroom, query, page-1, PagingConfig.SIZE));
        view.addAttribute("memberCount", memberCount);
        view.addAttribute("pageCount", PagingConfig.pageCountOf(memberCount));
        return "member";
    }

    @GetMapping("/{username}/remove")
    public String remove(
        RedirectAttributes redirect,
        @RequestAttribute Classroom classroom,
        @RequestAttribute ClassMember classMate,
        @RequestAttribute int page,
        @RequestAttribute String query
    ) {
        classMemberService.remove(classMate);
        redirect.addAttribute("page", page);
        redirect.addAttribute("query", query);
        return "redirect:/classroom/" + classroom.getId() + "/member";
    }

    @GetMapping("/{username}/promote")
    public String promote(
        RedirectAttributes redirect,
        @RequestAttribute Classroom classroom,
        @RequestAttribute ClassMember classMate,
        @RequestAttribute int page,
        @RequestAttribute String query
    ) {
        classMemberService.promote(classMate);
        redirect.addAttribute("page", page);
        redirect.addAttribute("query", query);
        return "redirect:/classroom/" + classroom.getId() + "/member";
    }

    @GetMapping("/{username}/demote")
    public String demote(
        RedirectAttributes redirect,
        @RequestAttribute Classroom classroom,
        @RequestAttribute ClassMember classMate,
        @RequestAttribute int page,
        @RequestAttribute String query
    ) {
        classMemberService.demote(classMate);
        redirect.addAttribute("page", page);
        redirect.addAttribute("query", query);
        return "redirect:/classroom/" + classroom.getId() + "/member";
    }

    @GetMapping("/leave")
    public String leave(@RequestAttribute ClassMember classMember) {
        classMemberService.remove(classMember);
        return "redirect:/classroom";
    }
}
