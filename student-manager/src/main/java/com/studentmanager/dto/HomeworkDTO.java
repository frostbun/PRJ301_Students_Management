package com.studentmanager.dto;

import java.util.List;

import com.studentmanager.model.Comment;
import com.studentmanager.model.Homework;
import com.studentmanager.model.Submission;

import lombok.Data;

@Data
public class HomeworkDTO {
    private Homework homework;
    private Submission submission;
    private List<Comment> comments;
}
