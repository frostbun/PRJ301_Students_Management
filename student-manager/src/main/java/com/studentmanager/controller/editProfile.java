package com.studentmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.studentmanager.service.UserService;

@Controller
@RequestMapping("/editProfile")
public class editProfile {
    @Autowired
    public UserService userService;

    @GetMapping
    public String get() {
        return "editProfile";
    }


}