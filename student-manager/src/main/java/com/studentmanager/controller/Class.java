package com.studentmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/class")
public class Class {

    @GetMapping
    public String classList() {
        return "class";
    }

    @GetMapping("/{id}")
    public String classID() {
        return "class";
    }
}
