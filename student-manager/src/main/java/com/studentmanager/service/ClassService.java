package com.studentmanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studentmanager.model.Class;
import com.studentmanager.model.User;
import com.studentmanager.repository.ClassDAO;
import com.studentmanager.repository.UserDAO;

@Service
public class ClassService {
    @Autowired
    private ClassDAO classDAO;
    @Autowired
    private UserDAO userDAO;

    public List<User> viewClassMembers(int classID, int page, int size) {
        return userDAO.readByClassID(classID, page, size);
    }

    public boolean createClass(Class _class) {
        return classDAO.create(_class);
    }
}
