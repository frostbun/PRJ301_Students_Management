package com.studentmanager.dto;

import com.studentmanager.model.Account;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ChangeAccountInformationDTO extends DTO {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String address;

    public String validate() {
        if (firstName.length() > 25 || lastName.length() > 25 || firstName.length() == 0 || lastName.length() == 0) {
            return NAME_MESSAGE;
        }
        if (!phone.matches(PHONE_REGEX)) {
            return PHONE_MESSAGE;
        }
        if (!email.matches(EMAIL_REGEX)) {
            return EMAIL_MESSAGE;
        }
        return null;
    }

    public Account mapToAccount(Account account) {
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setPhone(phone);
        account.setEmail(email);
        account.setAddress(address);
        return account;
    }
}
