package com.studentmanager.dto;

import org.springframework.ui.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class LoginDTO extends DTO {
    private String username;
    private String password;

    public String validate() {
        return null;
    }

    public Model addToView(Model view) {
        view.addAttribute("username", username);
        return view;
    }
}
