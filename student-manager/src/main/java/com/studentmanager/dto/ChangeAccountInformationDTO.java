package com.studentmanager.dto;

import org.springframework.ui.Model;

import com.studentmanager.model.Account;

import lombok.Data;

@Data
public class ChangeAccountInformationDTO implements DTO {
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

    public void addToView(Model view) {
        view.addAttribute("firstName", firstName);
        view.addAttribute("lastName", lastName);
        view.addAttribute("phone", phone);
        view.addAttribute("email", email);
        view.addAttribute("address", address);
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
