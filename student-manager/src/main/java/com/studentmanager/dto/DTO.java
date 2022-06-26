package com.studentmanager.dto;

import org.springframework.ui.Model;

public abstract class DTO {
    public static final String USERNAME_REGEX = "^(?=.{6,25}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9_]+(?<![_.])$";
    public static final String USERNAME_MESSAGE = "Username must be 6-25 characters long and contain only alphanumerics";

    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";
    public static final String PASSWORD_MESSAGE = "Password minimum 8 characters, at least 1 uppercase letter, 1 lowercase letter and 1 number";

    public static final String NAME_MESSAGE = "Name can not blank or longer than 25 characters";

    public static final String PHONE_REGEX = "^+?\\d{10,11}$";
    public static final String PHONE_MESSAGE = "Invalid phone number";

    public static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
    public static final String EMAIL_MESSAGE = "Invalid email address";

    public abstract String validate();

    public abstract Model addToView(Model view);

    public Model addToView(Model view, String error) {
        view.addAttribute("error", error);
        return addToView(view);
    }
}
