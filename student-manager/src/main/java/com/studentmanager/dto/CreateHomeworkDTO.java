package com.studentmanager.dto;

import java.time.Instant;

import org.springframework.ui.Model;

import com.studentmanager.model.Homework;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateHomeworkDTO extends CreateSubmissionDTO {
    private String description;
    private Instant deadline;

    public String validate() {
        if (deadline.compareTo(Instant.now()) < 0) {
            return "??? :D";
        }
        return super.validate();
    }

    public Model addToView(Model view) {
        view.addAttribute("description", description);
        view.addAttribute("deadline", deadline);
        return super.addToView(view);
    }

    public Homework mapToHomework(Homework homework) {
        homework.setDescription(description);
        homework.setDeadline(deadline);
        return homework;
    }
}
