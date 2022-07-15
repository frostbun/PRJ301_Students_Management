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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.studentmanager.config.PagingConfig;
import com.studentmanager.dto.CreateSubmissionDTO;
import com.studentmanager.dto.ServiceResponse;
import com.studentmanager.model.Classroom;
import com.studentmanager.model.Submission;
import com.studentmanager.service.ClassMemberService;
import com.studentmanager.service.SessionService;
import com.studentmanager.service.SubmissionService;

import javafx.util.Pair;

@Controller
@RequestMapping("/classroom/{cid}/homework/{hid}/submission")
public class SubmissionController {
    @Autowired
    private SessionService session;
    @Autowired
    private ClassMemberService classMemberService;
    @Autowired
    private SubmissionService submissionService;

    @GetMapping
    public String list(
            Model view,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "") String query,
            @RequestParam(required = false) String error
    ) {
        Classroom classroom = session.getCurrentClassroom();
        Long studentCount = classMemberService.countStudents(classroom);
        view.addAttribute(
            "students",
            classMemberService
                .getStudents(classroom, query, page-1, PagingConfig.SIZE)
                .stream()
                .map((student) -> new Pair<>(student, submissionService.getSubmission(student, session.getCurrentHomework())))
                .collect(Collectors.toList())
        );
        view.addAttribute("studentCount", studentCount);
        view.addAttribute("page", page);
        view.addAttribute("query", query);
        view.addAttribute("error", error);
        view.addAttribute("pageCount", PagingConfig.pageCountOf(studentCount));
        return "submission";
    }

    @PostMapping("/{sid}/mark")
    public String mark(
            RedirectAttributes redirect,
            @PathVariable Long cid,
            @PathVariable Long hid,
            Double mark,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "") String query
    ) {
        redirect.addAttribute("error", submissionService.mark(session.getCurrentSubmission(), mark).getError());
        redirect.addAttribute("page", page);
        redirect.addAttribute("query", query);
        return "redirect:/classroom/" + cid + "/homework/" + hid + "/submission";
    }

    @PostMapping("/create")
    public String create(RedirectAttributes redirect, @PathVariable Long cid, CreateSubmissionDTO dto, @RequestParam(defaultValue = "1") int page) {
        ServiceResponse<Submission> response = submissionService.create(session.getCurrentAccount(), session.getCurrentHomework(), dto);
        if (response.isError()) {
            redirect.addAttribute("error", response.getError());
        }
        redirect.addAttribute("page", page);
        return "redirect:/classroom/" + cid + "/homework";
    }

    @GetMapping("/{sid}/download")
    public ResponseEntity<Resource> download() throws IOException {
        Resource file = new FileSystemResource(session.getCurrentSubmission().getFilePath());
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
