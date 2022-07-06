package com.studentmanager.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CreateClassroomDTO extends DTO {
    private String name;

    public String validate() {
        int l = name.length();
        if (l < 1 || l > 50) {
            return "Classroom name must be between 1 and 50 characters";
        }
        return null;
    }
}
