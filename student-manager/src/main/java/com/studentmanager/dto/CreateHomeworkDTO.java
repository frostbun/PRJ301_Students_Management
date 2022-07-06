package com.studentmanager.dto;

import java.time.Instant;

import com.studentmanager.model.Homework;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateHomeworkDTO extends CreateSubmissionDTO {
    private String title;
    private String description;
    private Double maxMark;
    private String deadline;
    private Instant deadlineInstant;

    public String validate() {
        if (maxMark != null && maxMark < 0) {
            return "Max mark must be greater than 0";
        }
        deadlineInstant = Instant.parse(deadline + ":00Z");
        if (deadlineInstant != null && deadlineInstant.compareTo(Instant.now()) < 0) {
            return "Deadline must be in the future";
        }
        return super.validate();
    }

    public Homework mapToHomework(Homework homework) {
        homework.setTitle(title);
        homework.setDescription(description);
        homework.setMaxMark(maxMark);
        homework.setDeadline(deadlineInstant);
        return homework;
    }
}
