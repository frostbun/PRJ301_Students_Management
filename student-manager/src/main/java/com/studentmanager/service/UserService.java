package com.studentmanager.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.studentmanager.model.User;
import com.studentmanager.repository.UserDAO;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private HttpSession session;

    public boolean login(User user) {
        User realUser = userDAO.readByUsername(user.getUsername());
        if (realUser == null) return false;
        if (!passwordEncoder.matches(user.getPassword(), realUser.getPassword())) return false;
        session.setAttribute("user", realUser);
        System.out.println(realUser);
        return true;
    }

    public boolean register(User user) {
        if (userDAO.readByUsername(user.getUsername()) != null) return false;
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDAO.create(user);
    }
}
