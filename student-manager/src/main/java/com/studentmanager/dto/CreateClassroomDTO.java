package com.studentmanager.dto;

import org.springframework.ui.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CreateClassroomDTO extends DTO {
    private String name;

    public String validate() {
        return null;
    }

    public Model addToView(Model view) {
        view.addAttribute("name", name);
        return view;
    }
}
