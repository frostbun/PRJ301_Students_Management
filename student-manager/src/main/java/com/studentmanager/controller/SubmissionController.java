package com.studentmanager.controller;

import java.io.IOException;

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
import com.studentmanager.model.Account;
import com.studentmanager.model.ClassMember;
import com.studentmanager.model.Classroom;
import com.studentmanager.model.Homework;
import com.studentmanager.model.Submission;
import com.studentmanager.service.ClassMemberService;
import com.studentmanager.service.HomeworkService;
import com.studentmanager.service.SessionService;
import com.studentmanager.service.SubmissionService;

@Controller
@RequestMapping("/class/{cid}/homework/{hid}/submission")
public class SubmissionController {
    @Autowired
    private SessionService session;
    @Autowired
    private ClassMemberService classMemberService;
    @Autowired
    private HomeworkService homeworkService;
    @Autowired
    private SubmissionService submissionService;

    @GetMapping
    public String list(Model view, @PathVariable Long cid, @PathVariable Long hid, @RequestParam(defaultValue = "1") int page) {
        Homework homework = homeworkService.getHomework(classMemberService.getClassroom(session.getCurrentAccount(), cid), hid);
        if (homework == null) {
            return "redirect:/login";
        }
        view.addAttribute("submissions", submissionService.getSubmissions(homework, page-1, PagingConfig.SIZE));
        view.addAttribute("page", page);
        view.addAttribute("pageCount", PagingConfig.pageCountOf(submissionService.countSubmissions(homework)));
        return "submission";
    }

    @PostMapping("/create")
    public String create(RedirectAttributes redirect, @PathVariable Long cid, @PathVariable Long hid, CreateSubmissionDTO dto, @RequestParam(defaultValue = "1") int page) {
        Account account = session.getCurrentAccount();
        Classroom classroom = classMemberService.getClassroom(account, cid);
        ClassMember classMember = classMemberService.getClassMember(session.getCurrentAccount(), cid);
        if (classMember == null || classMember.getRole().equals(ClassMember.TEACHER)) {
            return "redirect:/login";
        }
        Homework homework = homeworkService.getHomework(classroom, hid);
        ServiceResponse<Submission> response = submissionService.create(account, homework, dto);
        if (response.isError()) {
            redirect.addAttribute("error", response.getError());
        }
        redirect.addAttribute("page", page);
        return "redirect:/class/" + cid + "homework";
    }

    @GetMapping("/{sid}/download")
    public ResponseEntity<Resource> download(@PathVariable Long cid, @PathVariable Long sid) throws IOException {
        Submission submission = submissionService.getSubmission(classMemberService.getClassroom(session.getCurrentAccount(), cid), sid);
        if (submission == null) {
            return ResponseEntity.notFound().build();
        }
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
