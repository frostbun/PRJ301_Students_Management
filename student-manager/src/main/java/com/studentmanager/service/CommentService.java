package com.studentmanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studentmanager.dto.ServiceResponse;
import com.studentmanager.model.Account;
import com.studentmanager.model.Comment;
import com.studentmanager.model.Homework;
import com.studentmanager.repository.CommentRepository;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepo;

    public ServiceResponse<Comment> comment(Account author, Homework homework, String comment) {
        if (comment == null) {
            return ServiceResponse.error("Invalid comment");
        }
        Comment c = new Comment();
        c.setAuthor(author);
        c.setHomework(homework);
        c.setContent(comment);
        return ServiceResponse.success(commentRepo.save(c));
    }

    public List<Comment> listCommentByHomework(Homework homework){
        return commentRepo.findByHomework(homework);
    }
}
