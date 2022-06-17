package com.studentmanager.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studentmanager.model.User;
import com.studentmanager.repository.UserDAO;

@Service
public class SessionService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private HttpSession session;

    public User getCurrentUser() {
        return (User)session.getAttribute("user");
    }

    public void setCurrentUser(String username) {
        if (username == null) {
            session.removeAttribute("user");
            return;
        }
        session.setAttribute("user", userDAO.readByUsername(username));
    }
}
