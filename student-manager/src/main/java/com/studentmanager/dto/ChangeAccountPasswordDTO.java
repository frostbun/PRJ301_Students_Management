package com.studentmanager.dto;

import org.springframework.ui.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ChangeAccountPasswordDTO extends DTO {
    private String password;
    private String newPassword;
    private String confirmPassword;

    public String validate() {
        if (!newPassword.matches(PASSWORD_REGEX)) {
            return PASSWORD_MESSAGE;
        }
        return null;
    }

    public Model addToView(Model view) {
        return view;
    }
}
