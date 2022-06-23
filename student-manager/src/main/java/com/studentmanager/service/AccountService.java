package com.studentmanager.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.studentmanager.dto.ChangeAccountInformationDTO;
import com.studentmanager.dto.ChangeAccountPasswordDTO;
import com.studentmanager.dto.LoginDTO;
import com.studentmanager.dto.RegisterDTO;
import com.studentmanager.model.Account;
import com.studentmanager.repository.AccountRepository;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Account login(Model view, LoginDTO dto) {
        String error = dto.validate();
        if (error != null) {
            view.addAttribute("error", error);
            return null;
        }
        Optional<Account> aOptional = accountRepo.findByUsername(dto.getUsername());
        if (!aOptional.isPresent()) {
            view.addAttribute("error", "Wrong username");
            return null;
        }
        Account account = aOptional.get();
        if (!passwordEncoder.matches(dto.getPassword(), account.getPassword())) {
            view.addAttribute("error", "Wrong password");
            return null;
        }
        return account;
    }

    public Account register(Model view, RegisterDTO dto) {
        String error = dto.validate();
        if (error != null) {
            view.addAttribute("error", error);
            return null;
        }
        Optional<Account> aOptional = accountRepo.findByUsername(dto.getUsername());
        if (aOptional.isPresent()) {
            view.addAttribute("error", "Username existed");
            return null;
        }
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            view.addAttribute("error", "Password not match");
            return null;
        }
        return accountRepo.save(dto.mapToAccount(new Account()));
    }

    public boolean changePassword(Model view, Account account, ChangeAccountPasswordDTO dto) {
        String error = dto.validate();
        if (error != null) {
            view.addAttribute("error", error);
            return false;
        }
        if (!passwordEncoder.matches(dto.getPassword(), account.getPassword())) {
            view.addAttribute("error", "Wrong password");
            return false;
        }
        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            view.addAttribute("error", "Password not match");
            return false;
        }
        account.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        accountRepo.save(account);
        return true;
    }

    public boolean changeInformation(Model view, Account account, ChangeAccountInformationDTO dto) {
        String error = dto.validate();
        if (error != null) {
            view.addAttribute("error", error);
            return false;
        }
        dto.mapToAccount(account);
        accountRepo.save(account);
        return true;
    }
}
