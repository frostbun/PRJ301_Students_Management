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

    public Account getCurrentAccount() {
        Account account = (Account)session.getAttribute("account");
        if (account == null) {
            return null;
        }
        Optional<Account> aOptional = accountRepo.findByUsername(account.getUsername());
        if (!aOptional.isPresent()) {
            setCurrentAccount(null);
            return null;
        }
        account = aOptional.get();
        setCurrentAccount(account);
        return account;
    }

    public void setCurrentAccount(Account account) {
        session.setAttribute("account", account);
    }
}
