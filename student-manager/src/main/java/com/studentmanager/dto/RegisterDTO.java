package com.studentmanager.dto;

import com.studentmanager.model.Account;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RegisterDTO extends ChangeAccountInformationDTO {
    private String username;
    private String password;
    private String confirmPassword;

    public String validate() {
        if (!username.matches(USERNAME_REGEX)) {
            return USERNAME_MESSAGE;
        }
        if (!password.matches(PASSWORD_REGEX)) {
            return PASSWORD_MESSAGE;
        }
        if (!password.equals(confirmPassword)) {
            return "Passwords not match";
        }
        return super.validate();
    }

    public Account mapToAccount(Account account) {
        account.setUsername(username);
        account.setPassword(password);
        return super.mapToAccount(account);
    }
}
