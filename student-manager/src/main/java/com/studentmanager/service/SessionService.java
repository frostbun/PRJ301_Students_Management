package com.studentmanager.service;

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
        // setCurrentAccount(Account.builder().username("admin1").build());
        // find in session
        Account account = (Account)session.getAttribute("account");
        if (account == null) {
            return null;
        }
        // check in database
        account = accountRepo.findByUsername(account.getUsername()).orElse(null);
        setCurrentAccount(account);
        return account;
    }

    public void setCurrentAccount(Account account) {
        session.setAttribute("account", account);
    }
}
