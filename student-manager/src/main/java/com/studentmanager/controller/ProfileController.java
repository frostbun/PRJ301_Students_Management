package com.studentmanager.controller;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String profile(Model view) {
        Account account = session.getCurrentAccount();
        if (account == null) {
            return "redirect:/";
        }
        view.addAttribute("account", account);
        return "profile";
    }

    @GetMapping("/{username}")
    public String profileById(Model view, @PathVariable String username) {
        throw new NotYetImplementedException();
    }

    @PostMapping("/edit/information")
    public String changeInformation(Model view, ChangeAccountInformationDTO dto) {
        Account account = session.getCurrentAccount();
        if (account == null) {
            return "redirect:/";
        }
        ServiceResponse<Account> response = accountService.changeInformation(account, dto);
        if (response.isError()){
            dto.addToView(view, response.getError());
            return "editProfile";
        }
        return "redirect:/profile";
    }

    @PostMapping("/edit/password")
    public String changePassword(Model view, ChangeAccountPasswordDTO dto) {
        Account account = session.getCurrentAccount();
        if (account == null) {
            return "redirect:/";
        }
        ServiceResponse<Account> response = accountService.changePassword(account, dto);
        if (response.isError()){
            dto.addToView(view, response.getError());
            return "editProfile";
        }
        return "redirect:/profile";
    }
}
