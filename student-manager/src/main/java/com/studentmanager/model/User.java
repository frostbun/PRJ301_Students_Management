package com.studentmanager.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class User {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phome;
    private String email;
    private String address;
    private String avatarURL;
    private LocalDateTime createdAt;
    private boolean deleted;
}
