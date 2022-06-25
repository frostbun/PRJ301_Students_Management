package com.studentmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.studentmanager.dto.LoginDTO;
import com.studentmanager.model.Account;
import com.studentmanager.service.AccountService;
import com.studentmanager.service.SessionService;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private SessionService session;

    @GetMapping
    public String get(Model view) {
        Account account = session.getCurrentAccount();
        if (account == null) {
            return "login";
        }
        return "redirect:/";
    }

    @PostMapping
    public String post(Model view, LoginDTO dto) {
        Account account = session.getCurrentAccount();
        if (account == null) {
            account = accountService.login(view, dto);
            if (account == null) {
                dto.addToView(view);
                return "login";
            }
        }
        session.setCurrentAccount(account);
        return "redirect:/";
    }
}
