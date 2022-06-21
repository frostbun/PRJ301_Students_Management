package com.studentmanager.service;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studentmanager.model.Account;
import com.studentmanager.repository.AccountRepository;

@Service
public class SessionService {
    @Autowired
    private HttpSession session;
    @Autowired
    private AccountRepository accountRepo;

    public boolean loggedIn() {
        Account account = (Account)session.getAttribute("account");
        if (account == null) {
            return false;
        }
        Optional<Account> uOptional = accountRepo.findByUsername(account.getUsername());
        if (!uOptional.isPresent()) {
            setCurrentAccount(null);
            return false;
        }
        setCurrentAccount(uOptional.get());
        return true;
    }

    public Account getCurrentAccount() {
        return (Account)session.getAttribute("account");
    }

    public void setCurrentAccount(Account account) {
        session.setAttribute("account", account);
    }
}
