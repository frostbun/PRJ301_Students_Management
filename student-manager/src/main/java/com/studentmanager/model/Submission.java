package com.studentmanager.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Submission {
    private int homeworkID;
    private String username;
    private String fileLink;
    private int mark;
    private LocalDateTime createdAt;
}
