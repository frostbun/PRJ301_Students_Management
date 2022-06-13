package com.studentmanager.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Message {
    private int messageID;
    private int classID;
    private String sender;
    private String receiver;
    private String content;
    private LocalDateTime createdAt;
    private boolean deleted;
}
