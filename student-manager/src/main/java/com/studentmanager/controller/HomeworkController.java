package com.studentmanager.controller;

import java.io.IOException;

import org.hibernate.cfg.NotYetImplementedException;
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

import com.studentmanager.config.PagingConfig;
import com.studentmanager.dto.CreateHomeworkDTO;
import com.studentmanager.model.Account;
import com.studentmanager.model.ClassMember;
import com.studentmanager.model.Classroom;
import com.studentmanager.model.Homework;
import com.studentmanager.service.ClassroomService;
import com.studentmanager.service.HomeworkService;
import com.studentmanager.service.SessionService;

@Controller
@RequestMapping("/class/{cid}/homeworks")
public class HomeworkController {
    @Autowired
    private SessionService session;
    @Autowired
    private ClassroomService classroomService;
    @Autowired
    private HomeworkService homeworkService;

    @GetMapping
    public String homeworks(Model view, @PathVariable Long cid, @RequestParam(defaultValue = "0") int page) {
        Account account = session.getCurrentAccount();
        if (account == null) {
            return "redirect:/login";
        }
        Classroom classroom = classroomService.getClassroomById(cid, account);
        if (classroom == null) {
            return "redirect:/";
        }
        view.addAttribute("homeworks", classroomService.getClassroomHomeworks(classroom, page, PagingConfig.SIZE));
        view.addAttribute("pages", classroomService.countClassroomHomeWorks(classroom) / PagingConfig.SIZE);
        return "homeworks";
    }

    @GetMapping("/create")
    public String createGet(Model view, @PathVariable Long cid) {
        Account account = session.getCurrentAccount();
        if (account == null) {
            return "redirect:/login";
        }
        Classroom classroom = classroomService.getClassroomById(cid, account);
        if (classroom == null) {
            return "redirect:/";
        }
        String role = classroomService.getAccountRole(account, classroom);
        if (!role.equals(ClassMember.TEACHER)) {
            return "redirect:/";
        }
        return "create_homework";
    }

    @PostMapping("/create")
    public String createPost(Model view, @PathVariable Long cid, CreateHomeworkDTO dto) throws IllegalStateException, IOException {
        Account account = session.getCurrentAccount();
        if (account == null) {
            return "redirect:/login";
        }
        Classroom classroom = classroomService.getClassroomById(cid, account);
        if (classroom == null) {
            return "redirect:/";
        }
        String role = classroomService.getAccountRole(account, classroom);
        if (!role.equals(ClassMember.TEACHER)) {
            return "redirect:/";
        }
        Long hid = homeworkService.create(view, account, classroom, dto);
        if (hid == null) {
            dto.addToView(view);
            return "create_homework";
        }
        return "redirect:/class/" + cid;
    }

    @GetMapping("/{hid}/download")
    public ResponseEntity<Resource> download(@PathVariable Long cid, @PathVariable Long hid) throws IOException {
        Homework homework = homeworkService.getHomeworkById(
            hid,
            classroomService.getClassroomById(
                cid,
                session.getCurrentAccount()
            )
        );
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
        return "";
    }

    @PostMapping("/{hid}/edit")
    public String editHomeworkPost(Model view, @PathVariable Long cid, @PathVariable Long hid) {
        return "";
    }

    @GetMapping("{hid}/delete")
    public String deleteHomework(@PathVariable Long cid, @PathVariable Long hid) {
        throw new NotYetImplementedException();
    }
}
