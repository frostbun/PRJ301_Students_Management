package com.studentmanager.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Class {
    private int classID;
    private String className;
    private String inviteCode;
    private String coverURL;
    private LocalDateTime createdAt;
}
