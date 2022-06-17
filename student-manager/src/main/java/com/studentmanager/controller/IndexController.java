package com.studentmanager.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {
    @Autowired
    HttpSession session;

    @GetMapping
    public String index() {
        System.out.println(session.getAttribute("user"));
        return "index";
    }
}
