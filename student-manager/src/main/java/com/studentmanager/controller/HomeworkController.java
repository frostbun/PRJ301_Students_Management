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
import com.studentmanager.dto.CreateHomeworkDTO;
import com.studentmanager.dto.ServiceResponse;
import com.studentmanager.model.Classroom;
import com.studentmanager.model.Homework;
import com.studentmanager.service.ClassMemberService;
import com.studentmanager.service.HomeworkService;
import com.studentmanager.service.SessionService;
import com.studentmanager.service.SubmissionService;

import javafx.util.Pair;

@Controller
@RequestMapping("/classroom/{cid}/homework")
public class HomeworkController {
    @Autowired
    private SessionService session;
    @Autowired
    private ClassMemberService classMemberService;
    @Autowired
    private HomeworkService homeworkService;
    @Autowired
    private SubmissionService submissionService;

    @GetMapping
    public String list(Model view, @RequestParam(defaultValue = "1") int page, @RequestParam(required = false) String error) {
        Classroom classroom = session.getCurrentClassroom();
        view.addAttribute("studentCount", classMemberService.countStudents(classroom));
        view.addAttribute(
            "homeworks",
            homeworkService
                .getHomeworks(classroom, page-1, PagingConfig.SIZE)
                .stream()
                .map(homework -> new Pair<>(homework, submissionService.getSubmission(session.getCurrentAccount(), homework)))
                .collect(Collectors.toList())
        );
        view.addAttribute("page", page);
        view.addAttribute("pageCount", PagingConfig.pageCountOf(homeworkService.countHomeworks(classroom)));
        view.addAttribute("error", error);
        return "homework";
    }

    @GetMapping("/create")
    public String createGet() {
        return "createHomework";
    }

    @PostMapping("/create")
    public String createPost(Model view, @PathVariable Long cid, CreateHomeworkDTO dto) {
        ServiceResponse<Homework> response = homeworkService.create(session.getCurrentAccount(), session.getCurrentClassroom(), dto);
        if (response.isError()) {
            view.addAttribute("homework", dto);
            view.addAttribute("error", response.getError());
            return "createHomework";
        }
        return "redirect:/classroom/" + cid + "/homework";
    }

    @GetMapping("/{hid}/download")
    public ResponseEntity<Resource> download() throws IOException {
        Resource file = new FileSystemResource(session.getCurrentHomework().getFilePath());
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
    public String editGet(Model view, @RequestParam(defaultValue = "1") int page) {
        view.addAttribute("homework", session.getCurrentHomework());
        view.addAttribute("page", page);
        return "editHomework";
    }

    @PostMapping("/{hid}/edit")
    public String editPost(Model view, RedirectAttributes redirect, @PathVariable Long cid, @PathVariable Long hid, @RequestParam(defaultValue = "1") int page, CreateHomeworkDTO dto) {
        ServiceResponse<Homework> response = homeworkService.edit(session.getCurrentHomework(), dto);
        if (response.isError()) {
            view.addAttribute("homework", dto);
            view.addAttribute("page", page);
            view.addAttribute("error", response.getError());
            return "editHomework";
        }
        redirect.addAttribute("page", page);
        return "redirect:/classroom/" + cid + "/homework";
    }

    @GetMapping("{hid}/delete")
    public String delete(RedirectAttributes redirect, @PathVariable Long cid, @PathVariable Long hid, @RequestParam(defaultValue = "1") int page) {
        homeworkService.delete(session.getCurrentHomework());
        redirect.addAttribute("page", page);
        return "redirect:/classroom/" + cid + "/homework";
    }
}
