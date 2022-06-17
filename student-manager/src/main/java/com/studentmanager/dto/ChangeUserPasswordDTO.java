package com.studentmanager.dto;

import lombok.Data;

@Data
public class ChangeUserPasswordDTO implements DTO {
    private String password;
    private String newPassword;
    private String confirmPassword;

    @Override
    public String validate() {
        if (!newPassword.matches(PASSWORD_REGEX)) {
            return PASSWORD_MESSAGE;
        }
        return null;
    }
}
