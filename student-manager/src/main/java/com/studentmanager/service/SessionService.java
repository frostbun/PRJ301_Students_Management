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

    public boolean checkCurrentAccount() {
        // setCurrentAccount(Account.builder().username("thanhienee").build());
        // find in session
        Account account = getCurrentAccount();
        if (account == null) {
            return false;
        }
        // check in database
        account = accountRepo.findByUsername(account.getUsername()).orElse(null);
        setCurrentAccount(account);
        return account != null;
    }

    public Account getCurrentAccount() {
        return (Account)session.getAttribute("account");
    }

    public void setCurrentAccount(Account account) {
        session.setAttribute("account", account);
    }
}
