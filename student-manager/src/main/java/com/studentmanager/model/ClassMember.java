package com.studentmanager.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ClassMember {
    private int classID;
    private String username;
    private String role;
    private LocalDateTime createdAt;
    private boolean deleted;
}
