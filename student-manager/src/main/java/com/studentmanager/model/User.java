package com.studentmanager.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
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
}
