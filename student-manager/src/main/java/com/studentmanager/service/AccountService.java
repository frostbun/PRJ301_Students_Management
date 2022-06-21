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
    private SessionService session;
    @Autowired
    private AccountRepository accountRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean login(Model view, LoginDTO dto) {
        Optional<Account> uOptional = accountRepo.findByUsername(dto.getUsername());
        if (!uOptional.isPresent()) {
            view.addAttribute("error", "Wrong username");
            return false;
        }
        Account account = uOptional.get();
        if (!passwordEncoder.matches(dto.getPassword(), account.getPassword())) {
            view.addAttribute("error", "Wrong password");
            return false;
        }
        session.setCurrentAccount(account);
        return true;
    }

    public boolean register(Model view, RegisterDTO dto) {
        String error = dto.validate();
        if (error != null) {
            view.addAttribute("error", error);
            return false;
        }
        Optional<Account> uOptional = accountRepo.findByUsername(dto.getUsername());
        if (uOptional.isPresent()) {
            view.addAttribute("error", "Username existed");
            return false;
        }
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            view.addAttribute("error", "Password not match");
            return false;
        }
        Account account = dto.mapToAccount(new Account());
        accountRepo.save(account);
        session.setCurrentAccount(account);
        return true;
    }

    public boolean changePassword(Model view, ChangeAccountPasswordDTO dto) {
        String error = dto.validate();
        if (error != null) {
            view.addAttribute("error", error);
            return false;
        }
        Account account = session.getCurrentAccount();
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

    public boolean changeInformation(Model view, ChangeAccountInformationDTO dto) {
        String error = dto.validate();
        if (error != null) {
            view.addAttribute("error", error);
            return false;
        }
        Account account = session.getCurrentAccount();
        dto.mapToAccount(account);
        accountRepo.save(account);
        return true;
    }
}
