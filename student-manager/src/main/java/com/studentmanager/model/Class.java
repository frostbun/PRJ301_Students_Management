package com.studentmanager.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Class {
    private int classID;
    private String className;
    private String inviteCode;
    private String coverURL;
    private LocalDateTime createdAt;
    private boolean deleted;
}
