package com.studentmanager.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RegisterDTO extends ChangeUserInformationDTO {
    private String username;
    private String password;
    private String confirmPassword;

    @Override
    public String validate() {
        if (!username.matches(USERNAME_REGEX)) {
            return USERNAME_MESSAGE;
        }
        if (!password.matches(PASSWORD_REGEX)) {
            return PASSWORD_MESSAGE;
        }
        return super.validate();
    }
}
