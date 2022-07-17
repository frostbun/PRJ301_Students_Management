package com.studentmanager.controller;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.studentmanager.config.PagingConfig;
import com.studentmanager.dto.CreateHomeworkDTO;
import com.studentmanager.dto.ServiceResponse;
import com.studentmanager.model.Account;
import com.studentmanager.model.Classroom;
import com.studentmanager.model.Homework;
import com.studentmanager.service.ClassMemberService;
import com.studentmanager.service.HomeworkService;
import com.studentmanager.service.SubmissionService;

import javafx.util.Pair;

@Controller
@RequestMapping("/classroom/{cid}/homework")
public class HomeworkController {
    @Autowired
    private ClassMemberService classMemberService;
    @Autowired
    private HomeworkService homeworkService;
    @Autowired
    private SubmissionService submissionService;

    @GetMapping
    public String list(
        Model view,
        @RequestAttribute Account account,
        @RequestAttribute Classroom classroom,
        @RequestAttribute int page
    ) {
        view.addAttribute(
            "homeworks",
            homeworkService
            .getHomeworks(classroom, page-1, PagingConfig.SIZE)
            .stream()
            .map(homework -> new Pair<>(homework, submissionService.getSubmission(account, homework)))
            .collect(Collectors.toList())
            );
        view.addAttribute("studentCount", classMemberService.countStudents(classroom));
        view.addAttribute("pageCount", PagingConfig.pageCountOf(homeworkService.countHomeworks(classroom)));
        return "homework";
    }

    @GetMapping("/create")
    public String createGet() {
        return "createHomework";
    }

    @PostMapping("/create")
    public String createPost(
        Model view,
        CreateHomeworkDTO dto,
        @RequestAttribute Account account,
        @RequestAttribute Classroom classroom
    ) {
        ServiceResponse<Homework> response = homeworkService.create(account, classroom, dto);
        if (response.isError()) {
            view.addAttribute("homework", dto);
            view.addAttribute("error", response.getErrorMessage());
            return "createHomework";
        }
        return "redirect:/classroom/" + classroom.getId() + "/homework";
    }

    @GetMapping("/{hid}/download")
    public ResponseEntity<Resource> download(@RequestAttribute Homework homework) throws IOException {
        Resource file = new FileSystemResource(homework.getFilePath());
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .contentLength(file.contentLength())
            .header(
                HttpHeaders.CONTENT_DISPOSITION,
                ContentDisposition.attachment().filename(file.getFilename()).build().toString()
            )
            .body(file);
    }

    @GetMapping("/{hid}/edit")
    public String editGet() {
        return "editHomework";
    }

    @PostMapping("/{hid}/edit")
    public String editPost(
        Model view,
        RedirectAttributes redirect,
        CreateHomeworkDTO dto,
        @RequestAttribute Classroom classroom,
        @RequestAttribute Homework homework,
        @RequestAttribute int page
    ) {
        ServiceResponse<Homework> response = homeworkService.edit(homework, dto);
        if (response.isError()) {
            view.addAttribute("homework", dto);
            view.addAttribute("error", response.getErrorMessage());
            return "editHomework";
        }
        redirect.addAttribute("page", page);
        return "redirect:/classroom/" + classroom.getId() + "/homework";
    }

    @GetMapping("{hid}/delete")
    public String delete(
        RedirectAttributes redirect,
        @RequestAttribute Classroom classroom,
        @RequestAttribute Homework homework,
        @RequestAttribute int page
    ) {
        homeworkService.delete(homework);
        redirect.addAttribute("page", page);
        return "redirect:/classroom/" + classroom.getId() + "/homework";
    }
}
