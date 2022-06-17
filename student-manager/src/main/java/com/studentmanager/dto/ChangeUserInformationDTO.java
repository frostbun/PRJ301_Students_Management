package com.studentmanager.dto;

import lombok.Data;

@Data
public class ChangeUserInformationDTO implements DTO {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String address;

    @Override
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
}
