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
import com.studentmanager.dto.CreateSubmissionDTO;
import com.studentmanager.dto.ServiceResponse;
import com.studentmanager.model.Account;
import com.studentmanager.model.Classroom;
import com.studentmanager.model.Homework;
import com.studentmanager.model.Submission;
import com.studentmanager.service.ClassMemberService;
import com.studentmanager.service.SubmissionService;

import javafx.util.Pair;

@Controller
@RequestMapping("/classroom/{cid}/homework/{hid}/submission")
public class SubmissionController {
    @Autowired
    private ClassMemberService classMemberService;
    @Autowired
    private SubmissionService submissionService;

    @GetMapping
    public String list(
            Model view,
            @RequestAttribute Account account,
            @RequestAttribute Classroom classroom,
            @RequestAttribute Homework homework,
            @RequestAttribute int page,
            @RequestAttribute String query
    ) {
        Long studentCount = classMemberService.countStudents(classroom);
        view.addAttribute(
            "students",
            classMemberService
                .getStudents(classroom, query, page-1, PagingConfig.SIZE)
                .stream()
                .map((student) -> new Pair<>(student, submissionService.getSubmission(student, homework)))
                .collect(Collectors.toList())
        );
        view.addAttribute("studentCount", studentCount);
        view.addAttribute("pageCount", PagingConfig.pageCountOf(studentCount));
        return "submission";
    }

    @PostMapping("/create")
    public String create(
        RedirectAttributes redirect,
        CreateSubmissionDTO dto,
        @RequestAttribute Account account,
        @RequestAttribute Classroom classroom,
        @RequestAttribute Homework homework,
        @RequestAttribute int page
    ) {
        ServiceResponse<Submission> response = submissionService.create(account, homework, dto);
        if (response.isError()) {
            redirect.addAttribute("error", response.getErrorMessage());
        }
        redirect.addAttribute("page", page);
        return "redirect:/classroom/" + classroom.getId() + "/homework";
    }

    @PostMapping("/{sid}/mark")
    public String mark(
            RedirectAttributes redirect,
            Double mark,
            @RequestAttribute Classroom classroom,
            @RequestAttribute Homework homework,
            @RequestAttribute Submission submission,
            @RequestAttribute int page,
            @RequestAttribute String query
    ) {
        ServiceResponse<Submission> response = submissionService.mark(submission, mark);
        if (response.isError()) {
            redirect.addAttribute("error", response.getErrorMessage());
        }
        redirect.addAttribute("page", page);
        redirect.addAttribute("query", query);
        return "redirect:/classroom/" + classroom.getId() + "/homework/" + homework.getId() + "/submission";
    }

    @GetMapping("/{sid}/download")
    public ResponseEntity<Resource> download(@RequestAttribute Submission submission) throws IOException {
        Resource file = new FileSystemResource(submission.getFilePath());
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .contentLength(file.contentLength())
            .header(
                HttpHeaders.CONTENT_DISPOSITION,
                ContentDisposition.attachment().filename(file.getFilename()).build().toString()
            )
            .body(file);
    }
}
