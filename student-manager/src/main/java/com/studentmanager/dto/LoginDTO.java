package com.studentmanager.dto;

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
}
