package com.studentmanager.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/class/{cid}/homeworks")
public class HomeworkController {
    @GetMapping
    public String homeworks(Model view, @PathVariable Long cid) {
        return "class";
    }

    @GetMapping("/create")
    public String createGet(Model view, @PathVariable Long cid) {
        return "test";
    }
    
    @PostMapping("/create")
    public String createPost(Model view, @PathVariable Long cid, MultipartFile file) throws IllegalStateException, IOException {
        file.transferTo(new File("pathname.txt"));
        System.out.println(file.getSize());
        return "redirect:/class/" + cid + "/homeworks/create";
    }
}
