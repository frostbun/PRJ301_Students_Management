package com.studentmanager.dto;

import lombok.Data;

@Data
public class LoginDTO implements DTO {
    private String username;
    private String password;

    @Override
    public String validate() {
        return null;
    }
}
