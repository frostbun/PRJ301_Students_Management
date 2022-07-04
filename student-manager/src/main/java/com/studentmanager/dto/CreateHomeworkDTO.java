package com.studentmanager.dto;

import java.time.Instant;

import org.springframework.ui.Model;

import com.studentmanager.model.Homework;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateHomeworkDTO extends CreateSubmissionDTO {
    private String title;
    private String description;
    private int maxMark;
    private Instant deadline;

    public String validate() {
        if (maxMark < 0) {
            return "Max mark must be greater than 0";
        }
        if (deadline.compareTo(Instant.now()) < 0) {
            return "Deadline must be in the future";
        }
        return super.validate();
    }

    public Model addToView(Model view) {
        view.addAttribute("title", title);
        view.addAttribute("description", description);
        view.addAttribute("maxMark", maxMark);
        view.addAttribute("deadline", deadline);
        return super.addToView(view);
    }

    public Homework mapToHomework(Homework homework) {
        homework.setTitle(title);
        homework.setDescription(description);
        homework.setMaxMark(maxMark);
        homework.setDeadline(deadline);
        return homework;
    }
}
