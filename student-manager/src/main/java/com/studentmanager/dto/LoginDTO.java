package com.studentmanager.dto;

import org.springframework.ui.Model;

import lombok.Data;

@Data
public class LoginDTO implements DTO {
    private String username;
    private String password;

    public String validate() {
        return null;
    }

    public void addToView(Model view) {
        view.addAttribute("username", username);
    }
}
