package com.studentmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.studentmanager.config.PagingConfig;
import com.studentmanager.model.ClassMember;
import com.studentmanager.model.Classroom;
import com.studentmanager.service.ClassMemberService;
import com.studentmanager.service.SessionService;

@Controller
@RequestMapping("/classroom/{cid}/member")
public class ClassMemberController {
    @Autowired
    private SessionService session;
    @Autowired
    private ClassMemberService classMemberService;

    @GetMapping
    public String list(Model view, @PathVariable Long cid, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "") String query) {
        ClassMember classMember = classMemberService.getClassMember(session.getCurrentAccount(), cid);
        if (classMember == null) {
            return "redirect:/login";
        }
        Classroom classroom = classMember.getClassroom();
        Long memberCount = classMemberService.countClassMembers(classroom);
        view.addAttribute("classMember", classMember);
        view.addAttribute("members", classMemberService.getClassMembers(classroom, query, page-1, PagingConfig.SIZE));
        view.addAttribute("memberCount", memberCount);
        view.addAttribute("page", page);
        view.addAttribute("query", query);
        view.addAttribute("pageCount", PagingConfig.pageCountOf(memberCount));
        return "member";
    }

    @GetMapping("/{username}/remove")
    public String remove(RedirectAttributes redirect, @PathVariable Long cid, @PathVariable String username, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "") String query) {
        ClassMember classMember = classMemberService.getClassMember(session.getCurrentAccount(), cid);
        ClassMember otherMember = classMemberService.getClassMember(username, cid);
        if (classMember == null || classMember.isStudent() || otherMember == null) {
            return "redirect:/login";
        }
        classMemberService.remove(otherMember);
        redirect.addAttribute("page", page);
        redirect.addAttribute("query", query);
        return "redirect:/classroom/" + cid + "/member";
    }

    @GetMapping("/{username}/promote")
    public String promote(RedirectAttributes redirect, @PathVariable Long cid, @PathVariable String username, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "") String query) {
        ClassMember classMember = classMemberService.getClassMember(session.getCurrentAccount(), cid);
        ClassMember otherMember = classMemberService.getClassMember(username, cid);
        if (classMember == null || classMember.isStudent() || otherMember == null) {
            return "redirect:/login";
        }
        classMemberService.promote(otherMember);
        redirect.addAttribute("page", page);
        redirect.addAttribute("query", query);
        return "redirect:/classroom/" + cid + "/member";
    }

    @GetMapping("/{username}/demote")
    public String demote(RedirectAttributes redirect, @PathVariable Long cid, @PathVariable String username, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "") String query) {
        ClassMember classMember = classMemberService.getClassMember(session.getCurrentAccount(), cid);
        ClassMember otherMember = classMemberService.getClassMember(username, cid);
        if (classMember == null || classMember.isStudent() || otherMember == null) {
            return "redirect:/login";
        }
        classMemberService.demote(otherMember);
        redirect.addAttribute("page", page);
        redirect.addAttribute("query", query);
        return "redirect:/classroom/" + cid + "/member";
    }

    @GetMapping("/leave")
    public String leave(@PathVariable Long cid) {
        ClassMember classMember = classMemberService.getClassMember(session.getCurrentAccount(), cid);
        if (classMember == null) {
            return "redirect:/login";
        }
        classMemberService.remove(classMember);
        return "redirect:/classroom";
    }
}
