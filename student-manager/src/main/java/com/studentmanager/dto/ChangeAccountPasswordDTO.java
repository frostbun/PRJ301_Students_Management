package com.studentmanager.dto;

import org.springframework.ui.Model;

import lombok.Data;

@Data
public class ChangeAccountPasswordDTO implements DTO {
    private String password;
    private String newPassword;
    private String confirmPassword;

    public String validate() {
        if (!newPassword.matches(PASSWORD_REGEX)) {
            return PASSWORD_MESSAGE;
        }
        return null;
    }

    public void addToView(Model view) {
    }
}
