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
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.studentmanager.config.PagingConfig;
import com.studentmanager.dto.CreateHomeworkDTO;
import com.studentmanager.dto.HomeworkDTO;
import com.studentmanager.dto.ServiceResponse;
import com.studentmanager.model.Account;
import com.studentmanager.model.Classroom;
import com.studentmanager.model.Comment;
import com.studentmanager.model.Homework;
import com.studentmanager.service.ClassMemberService;
import com.studentmanager.service.CommentService;
import com.studentmanager.service.HomeworkService;
import com.studentmanager.service.SubmissionService;

@Controller
@RequestMapping("/classroom/{cid}/homework")
public class HomeworkController {
    @Autowired
    private ClassMemberService classMemberService;
    @Autowired
    private HomeworkService homeworkService;
    @Autowired
    private SubmissionService submissionService;
    @Autowired
    private CommentService commentService;

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
                .map(homework -> {
                    HomeworkDTO dto = new HomeworkDTO();
                    dto.setHomework(homework);
                    dto.setSubmission(submissionService.getSubmission(account, homework));
                    dto.setComments(commentService.listCommentByHomework(homework));
                    return dto;
                })
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

    @PostMapping("/{hid}/comment")
    public String comment(
        RedirectAttributes redirect,
        String comment,
        @RequestAttribute Account account,
        @RequestAttribute Classroom classroom,
        @RequestAttribute Homework homework,
        @RequestAttribute int page
    ) {
        ServiceResponse<Comment> response =  commentService.comment(account, homework, comment);
        if (response.isError()) {
            redirect.addAttribute("error", response.getErrorMessage());
        }
        redirect.addAttribute("page", page);
        return "redirect:/classroom/" + classroom.getId() + "/homework";
    }

    @PostMapping("/{hid}/comment/{cmtid}/edit/{cmtid}/edit")
    public String comment(
        RedirectAttributes redirect,
        String comment,
        @PathVariable Long cmtid, 
        @RequestAttribute Account account,
        @RequestAttribute Classroom classroom,
        @RequestAttribute Homework homework,
        @RequestAttribute int page
    ) {
        commentService.editComment(cmtid, comment);
        redirect.addAttribute("page", page);
        return "redirect:/classroom/" + classroom.getId() + "/homework";
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
