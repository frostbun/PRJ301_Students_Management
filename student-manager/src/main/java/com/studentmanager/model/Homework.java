package com.studentmanager.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Homework {
    private int homeworkID;
    private int classID;
    private String creator;
    private String fileLink;
    private String decription;
    private LocalDateTime deadline;
    private LocalDateTime createdAt;
}
