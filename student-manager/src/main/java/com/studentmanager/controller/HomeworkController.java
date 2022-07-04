package com.studentmanager.controller;

import java.io.IOException;
import java.util.stream.Collectors;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.util.Pair;
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

import com.studentmanager.config.PagingConfig;
import com.studentmanager.dto.CreateHomeworkDTO;
import com.studentmanager.dto.ServiceResponse;
import com.studentmanager.model.Account;
import com.studentmanager.model.ClassMember;
import com.studentmanager.model.Classroom;
import com.studentmanager.model.Homework;
import com.studentmanager.service.ClassMemberService;
import com.studentmanager.service.HomeworkService;
import com.studentmanager.service.SessionService;
import com.studentmanager.service.SubmissionService;

@Controller
@RequestMapping("/class/{cid}/homework")
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
    public String homework(Model view, @PathVariable Long cid, @RequestParam(defaultValue = "0") int page) {
        ClassMember classMember = classMemberService.getClassMember(session.getCurrentAccount(), cid);
        if (classMember == null) {
            return "redirect:/";
        }
        Classroom classroom = classMember.getClassroom();
        view.addAttribute("role", classMember.getRole());
        view.addAttribute("classroom", classroom);
        view.addAttribute("countStudents", classMemberService.countStudents(classroom));
        view.addAttribute(
            "homeworks",
            homeworkService
                .getHomeworks(classroom, page, PagingConfig.SIZE)
                .stream()
                .map(homework -> Pair.of(homework, submissionService.getSubmission(session.getCurrentAccount(), homework)))
                .collect(Collectors.toList())
        );
        view.addAttribute("pages", homeworkService.countHomeworks(classroom) / PagingConfig.SIZE);
        return "homework";
    }

    @GetMapping("/create")
    public String createGet(Model view, @PathVariable Long cid) {
        ClassMember classMember = classMemberService.getClassMember(session.getCurrentAccount(), cid);
        if (classMember == null || !classMember.getRole().equals(ClassMember.TEACHER)) {
            return "redirect:/";
        }
        return "create_homework";
    }

    @PostMapping("/create")
    public String createPost(Model view, @PathVariable Long cid, CreateHomeworkDTO dto) throws IllegalStateException, IOException {
        Account account = session.getCurrentAccount();
        Classroom classroom = classMemberService.getClassroom(account, cid);
        ClassMember classMember = classMemberService.getClassMember(account, classroom);
        if (classMember == null || classMember.getRole().equals(ClassMember.STUDENT)) {
            return "redirect:/";
        }
        ServiceResponse<Homework> response = homeworkService.create(account, classroom, dto);
        if (response.isError()) {
            dto.addToView(view, response.getError());
            return "create_homework";
        }
        return "redirect:/class/" + cid;
    }

    @GetMapping("/{hid}/download")
    public ResponseEntity<Resource> download(@PathVariable Long cid, @PathVariable Long hid) throws IOException {
        Homework homework = homeworkService.getHomework(classMemberService.getClassroom(session.getCurrentAccount(), cid), hid);
        if (homework == null) {
            return ResponseEntity.notFound().build();
        }
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
    public String editHomeworkGet(Model view, @PathVariable Long cid, @PathVariable Long hid) {
        throw new NotYetImplementedException();
    }

    @PostMapping("/{hid}/edit")
    public String editHomeworkPost(Model view, @PathVariable Long cid, @PathVariable Long hid) {
        throw new NotYetImplementedException();
    }

    @GetMapping("{hid}/delete")
    public String deleteHomework(@PathVariable Long cid, @PathVariable Long hid) {
        throw new NotYetImplementedException();
    }
}
