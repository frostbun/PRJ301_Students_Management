package com.studentmanager.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Submission {
    private int homeworkID;
    private String username;
    private String fileLink;
    private int mark;
    private LocalDateTime createdAt;
    private boolean deleted;
}
