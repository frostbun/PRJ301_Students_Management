package com.studentmanager.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClassMember {
    private int classID;
    private String username;
    private String role;
    private LocalDateTime createdAt;
}
