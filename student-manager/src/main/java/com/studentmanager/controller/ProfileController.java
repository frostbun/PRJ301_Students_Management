package com.studentmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.studentmanager.dto.ChangeAccountInformationDTO;
import com.studentmanager.dto.ChangeAccountPasswordDTO;
import com.studentmanager.dto.ServiceResponse;
import com.studentmanager.model.Account;
import com.studentmanager.service.AccountService;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private AccountService accountService;

    @GetMapping
    public String profile(Model view) {
        return "profile";
    }

    @PostMapping("/edit/information")
    public String changeInformation(RedirectAttributes redirect, ChangeAccountInformationDTO dto, @RequestAttribute Account account) {
        ServiceResponse<Account> response = accountService.changeInformation(account, dto);
        redirect.addAttribute("error", response.isError() ? response.getErrorMessage() : "Information changed successfully");
        return "redirect:/profile";
    }

    @PostMapping("/edit/password")
    public String changePassword(RedirectAttributes redirect, ChangeAccountPasswordDTO dto, @RequestAttribute Account account) {
        ServiceResponse<Account> response = accountService.changePassword(account, dto);
        redirect.addAttribute("error", response.isError() ? response.getErrorMessage() : "Password changed successfully");
        return "redirect:/profile";
    }
}
