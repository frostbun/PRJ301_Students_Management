package com.studentmanager.dto;

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
}
