package com.studentmanager.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {
    private int messageID;
    private int classID;
    private String sender;
    private String receiver;
    private String content;
    private LocalDateTime createdAt;
}
