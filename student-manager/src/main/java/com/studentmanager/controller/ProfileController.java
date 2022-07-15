package com.studentmanager.controller;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.studentmanager.dto.ChangeAccountInformationDTO;
import com.studentmanager.dto.ChangeAccountPasswordDTO;
import com.studentmanager.dto.ServiceResponse;
import com.studentmanager.model.Account;
import com.studentmanager.service.AccountService;
import com.studentmanager.service.SessionService;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private SessionService session;

    @GetMapping
    public String profile(Model view, @RequestParam(required = false) String error) {
        view.addAttribute("error", error);
        return "profile";
    }

    @GetMapping("/{username}")
    public String profileById(Model view, @PathVariable String username) {
        throw new NotYetImplementedException();
    }

    @PostMapping("/edit/information")
    public String changeInformation(RedirectAttributes redirect, ChangeAccountInformationDTO dto) {
        ServiceResponse<Account> response = accountService.changeInformation(session.getCurrentAccount(), dto);
        redirect.addAttribute("error", response.isError() ? response.getError() : "Information changed successfully");
        return "redirect:/profile";
    }

    @PostMapping("/edit/password")
    public String changePassword(RedirectAttributes redirect, ChangeAccountPasswordDTO dto) {
        ServiceResponse<Account> response = accountService.changePassword(session.getCurrentAccount(), dto);
        redirect.addAttribute("error", response.isError() ? response.getError() : "Password changed successfully");
        return "redirect:/profile";
    }
}
